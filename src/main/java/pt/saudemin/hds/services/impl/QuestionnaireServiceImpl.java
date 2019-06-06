package pt.saudemin.hds.services.impl;

import lombok.extern.slf4j.Slf4j;
import lombok.var;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.saudemin.hds.dtos.QuestionnaireDTO;
import pt.saudemin.hds.mappers.QuestionnaireMapper;
import pt.saudemin.hds.repositories.QuestionnaireRepository;
import pt.saudemin.hds.services.QuestionnaireService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QuestionnaireServiceImpl implements QuestionnaireService {

    private QuestionnaireMapper questionnaireMapper = Mappers.getMapper(QuestionnaireMapper.class);

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Override
    public List<QuestionnaireDTO> getAll() {
        return questionnaireRepository.findAll().stream().map(questionnaireMapper::questionnaireToQuestionnaireDTO).collect(Collectors.toList());
    }

    @Override
    public QuestionnaireDTO getById(long id) {
        var questionnaire = questionnaireRepository.findById(id);

        return questionnaire.map(questionnaireMapper::questionnaireToQuestionnaireDTO).orElse(null);
    }

    @Override
    public QuestionnaireDTO create(QuestionnaireDTO questionnaireDTO) {
        var questionnaire = questionnaireMapper.questionnaireDTOToQuestionnaire(questionnaireDTO);
        QuestionnaireDTO createdQuestionnaire = null;

        try {
            createdQuestionnaire = questionnaireMapper.questionnaireToQuestionnaireDTO(questionnaireRepository.save(questionnaire));
        } catch (Exception e) {
            log.error("Error creating object of type " + this.getClass().getSimpleName() + ". Exception details: " + e.getMessage());
        }

        return createdQuestionnaire;
    }

    @Override
    public Boolean delete(long id) {
        try {
            questionnaireRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error deleting object of type " + this.getClass().getSimpleName() + ". Exception details: " + e.getMessage());
            return false;
        }

        return true;
    }
}
