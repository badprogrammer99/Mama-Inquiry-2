package pt.saudemin.hds.services.impl;

import lombok.extern.slf4j.Slf4j;

import lombok.var;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.saudemin.hds.dtos.entities.ChoiceQuestionDTO;
import pt.saudemin.hds.dtos.entities.QuestionDTO;
import pt.saudemin.hds.entities.ChoiceQuestion;
import pt.saudemin.hds.entities.base.Question;
import pt.saudemin.hds.mappers.ChoiceQuestionMapper;
import pt.saudemin.hds.mappers.QuestionMapper;
import pt.saudemin.hds.repositories.QuestionRepository;
import pt.saudemin.hds.services.QuestionService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO: Following methods are non optimized, try to find a better way:
 * @see QuestionServiceImpl#getAll()
 * @see QuestionServiceImpl#getAllGenericQuestions()
 * @see QuestionServiceImpl#getAllChoiceQuestions()
 * If database gets big enough, instance checks and casts will very quickly become expensive and possibly diminish the performance.
 */
@Service
@Slf4j
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public List<? extends QuestionDTO> getAll() {
        return questionRepository.findAll()
                .stream()
                .map(question -> question instanceof ChoiceQuestion ?
                        ChoiceQuestionMapper.INSTANCE.questionToQuestionDTO((ChoiceQuestion) question) :
                        QuestionMapper.INSTANCE.questionToQuestionDTO(question))
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestionDTO> getAllGenericQuestions() {
        return questionRepository.findAll().stream()
                .filter(question -> !(question instanceof ChoiceQuestion))
                .map(QuestionMapper.INSTANCE::questionToQuestionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChoiceQuestionDTO> getAllChoiceQuestions() {
        return questionRepository.findAll().stream()
                .filter(ChoiceQuestion.class::isInstance)
                .map(ChoiceQuestion.class::cast)
                .map(ChoiceQuestionMapper.INSTANCE::questionToQuestionDTO)
                .collect(Collectors.toList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends QuestionDTO> T getById(long id) {
        var question = questionRepository.findById(id);

        return (T) question.map(q -> q instanceof ChoiceQuestion ?
                ChoiceQuestionMapper.INSTANCE.questionToQuestionDTO((ChoiceQuestion) q) :
                QuestionMapper.INSTANCE.questionToQuestionDTO(q)).orElse(null);
    }

    @Override
    @Transactional
    public QuestionDTO create(QuestionDTO questionDTO) {
        var question = QuestionMapper.INSTANCE.questionDTOToQuestion(questionDTO);

        return QuestionMapper.INSTANCE.questionToQuestionDTO(questionRepository.save(question));
    }

    @Override
    @Transactional
    public ChoiceQuestionDTO create(ChoiceQuestionDTO choiceQuestionDTO) {
        var choiceQuestion = ChoiceQuestionMapper.INSTANCE.questionDTOToQuestion(choiceQuestionDTO);

        return ChoiceQuestionMapper.INSTANCE.questionToQuestionDTO(questionRepository.save(choiceQuestion));
    }

    @Override
    @Transactional
    public QuestionDTO update(QuestionDTO questionDTO) {
        return QuestionMapper.INSTANCE.questionToQuestionDTO(updateQuestion(questionDTO));
    }

    @Override
    @Transactional
    public ChoiceQuestionDTO update(ChoiceQuestionDTO choiceQuestionDTO) {
        return ChoiceQuestionMapper.INSTANCE.questionToQuestionDTO(updateQuestion(choiceQuestionDTO));
    }

    @Override
    @Transactional
    public Boolean delete(long id) {
        try {
            questionRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error deleting object of superclass type " + Question.class.getSimpleName() + ". Exception details: " + e.getMessage());
            return false;
        }

        return true;
    }

    @SuppressWarnings("unchecked")
    private <T extends Question> T updateQuestion(QuestionDTO questionDTO) {
        if (questionDTO.getId() == null) return null;

        var questionById = questionRepository.findById(questionDTO.getId());

        if (!questionById.isPresent()) return null;

        var question = questionDTO instanceof ChoiceQuestionDTO ?
                ChoiceQuestionMapper.INSTANCE.questionDTOToQuestion((ChoiceQuestionDTO) questionDTO) :
                QuestionMapper.INSTANCE.questionDTOToQuestion(questionDTO);

        return (T) questionRepository.save(question);
    }
}
