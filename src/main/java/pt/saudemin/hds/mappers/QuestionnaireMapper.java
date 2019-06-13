package pt.saudemin.hds.mappers;

import org.mapstruct.Mapper;

import org.mapstruct.factory.Mappers;
import pt.saudemin.hds.dtos.entities.QuestionnaireDTO;
import pt.saudemin.hds.entities.Questionnaire;

@Mapper(uses = {QuestionMapper.class, ChoiceQuestionMapper.class, InquiryMapper.class})
public interface QuestionnaireMapper {

    QuestionnaireMapper INSTANCE = Mappers.getMapper(QuestionnaireMapper.class);
    QuestionnaireDTO questionnaireToQuestionnaireDTO(Questionnaire questionnaire);
    Questionnaire questionnaireDTOToQuestionnaire(QuestionnaireDTO questionnaireDTO);
}
