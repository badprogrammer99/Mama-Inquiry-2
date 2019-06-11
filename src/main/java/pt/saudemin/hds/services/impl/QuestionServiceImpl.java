package pt.saudemin.hds.services.impl;

import lombok.extern.slf4j.Slf4j;

import lombok.var;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.saudemin.hds.dtos.ChoiceQuestionDTO;
import pt.saudemin.hds.dtos.QuestionDTO;
import pt.saudemin.hds.entities.ChoiceQuestion;
import pt.saudemin.hds.entities.base.Question;
import pt.saudemin.hds.mappers.ChoiceQuestionMapper;
import pt.saudemin.hds.mappers.QuestionMapper;
import pt.saudemin.hds.repositories.QuestionRepository;
import pt.saudemin.hds.services.QuestionService;
import pt.saudemin.hds.utils.QuestionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository<Question> questionRepository;

    @Autowired
    private QuestionRepository<ChoiceQuestion> choiceQuestionRepository;

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
        var questions = questionRepository.findAll();

        return QuestionUtils.filterListOfQuestionsOnPredicate(questions, question -> !(question instanceof ChoiceQuestion));
    }

    @Override
    public List<ChoiceQuestionDTO> getAllChoiceQuestions() {
        var choiceQuestions = choiceQuestionRepository.findAll();

        return QuestionUtils.filterListOfChoiceQuestionsOnPredicate(choiceQuestions, ChoiceQuestion.class::isInstance);
    }

    @Override
    public QuestionDTO getById(long id) {
        var question = questionRepository.findById(id);

        return question.map(q -> q instanceof ChoiceQuestion ?
                ChoiceQuestionMapper.INSTANCE.questionToQuestionDTO((ChoiceQuestion) q) :
                QuestionMapper.INSTANCE.questionToQuestionDTO(q)).orElse(null);
    }

    @Override
    public QuestionDTO create(QuestionDTO questionDTO) {
        Question question = QuestionMapper.INSTANCE.questionDTOToQuestion(questionDTO);

        return QuestionMapper.INSTANCE.questionToQuestionDTO(createQuestion(question));
    }

    @Override
    public ChoiceQuestionDTO create(ChoiceQuestionDTO choiceQuestionDTO) {
        ChoiceQuestion choiceQuestion = ChoiceQuestionMapper.INSTANCE.questionDTOToQuestion(choiceQuestionDTO);

        return ChoiceQuestionMapper.INSTANCE.questionToQuestionDTO(createQuestion(choiceQuestion));
    }

    @Override
    public QuestionDTO update(QuestionDTO questionDTO) {
        return QuestionMapper.INSTANCE.questionToQuestionDTO(updateQuestion(questionDTO));
    }

    @Override
    public ChoiceQuestionDTO update(ChoiceQuestionDTO choiceQuestionDTO) {
        return ChoiceQuestionMapper.INSTANCE.questionToQuestionDTO(updateQuestion(choiceQuestionDTO));
    }

    @Override
    public Boolean delete(long id) {
        try {
            questionRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error deleting object of superclass type " + Question.class.getSimpleName() + ". Exception details: " + e.getMessage());
            return false;
        }

        return true;
    }

    private <T extends Question> T createQuestion(T question) {
        return questionRepository.save(question);
    }

    @SuppressWarnings("unchecked")
    private <T extends Question> T updateQuestion(QuestionDTO questionDTO) {
        if (questionDTO.getId() == null) return null;

        var questionById = questionRepository.findById(questionDTO.getId());

        if (!questionById.isPresent()) return null;

        var question = questionDTO instanceof ChoiceQuestionDTO ?
                ChoiceQuestionMapper.INSTANCE.questionDTOToQuestion((ChoiceQuestionDTO) questionDTO) :
                QuestionMapper.INSTANCE.questionDTOToQuestion(questionDTO);

        return questionRepository.save((T) question);
    }
}
