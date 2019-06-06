package pt.saudemin.hds.mappers;

import org.mapstruct.Mapper;

import pt.saudemin.hds.dtos.QuestionnaireDTO;
import pt.saudemin.hds.entities.Questionnaire;

@Mapper(uses = {UserMapper.class, InquiryMapper.class})
public interface QuestionnaireMapper {

    QuestionnaireDTO questionnaireToQuestionnaireDTO(Questionnaire questionnaire);
    Questionnaire questionnaireDTOToQuestionnaire(QuestionnaireDTO questionnaireDTO);
}
