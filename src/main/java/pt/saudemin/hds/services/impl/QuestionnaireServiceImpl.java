package pt.saudemin.hds.services.impl;

import lombok.extern.slf4j.Slf4j;
import lombok.var;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.saudemin.hds.dtos.QuestionnaireDTO;
import pt.saudemin.hds.entities.Questionnaire;
import pt.saudemin.hds.mappers.QuestionnaireMapper;
import pt.saudemin.hds.repositories.QuestionnaireRepository;
import pt.saudemin.hds.services.QuestionnaireService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QuestionnaireServiceImpl implements QuestionnaireService {

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Override
    public List<QuestionnaireDTO> getAll() {
        return questionnaireRepository.findAll()
                .stream()
                .map(QuestionnaireMapper.INSTANCE::questionnaireToQuestionnaireDTO)
                .collect(Collectors.toList());
    }

    @Override
    public QuestionnaireDTO getById(long id) {
        var questionnaire = questionnaireRepository.findById(id);

        return questionnaire.map(QuestionnaireMapper.INSTANCE::questionnaireToQuestionnaireDTO).orElse(null);
    }

    @Override
    public QuestionnaireDTO create(QuestionnaireDTO questionnaireDTO) {
        var questionnaire = QuestionnaireMapper.INSTANCE.questionnaireDTOToQuestionnaire(questionnaireDTO);

        return QuestionnaireMapper.INSTANCE.questionnaireToQuestionnaireDTO(questionnaireRepository.save(questionnaire));
    }

    @Override
    public QuestionnaireDTO update(QuestionnaireDTO questionnaireDTO) {
        if (questionnaireDTO.getId() == null) return null;

        var questionnaireById = questionnaireRepository.findById(questionnaireDTO.getId());

        if (!questionnaireById.isPresent()) return null;

        var questionnaire = QuestionnaireMapper.INSTANCE.questionnaireDTOToQuestionnaire(questionnaireDTO);
        return QuestionnaireMapper.INSTANCE.questionnaireToQuestionnaireDTO(questionnaireRepository.save(questionnaire));
    }

    @Override
    public Boolean delete(long id) {
        try {
            questionnaireRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error deleting object of type " + Questionnaire.class.getSimpleName() + ". Exception details: " + e.getMessage());
            return false;
        }

        return true;
    }
}
