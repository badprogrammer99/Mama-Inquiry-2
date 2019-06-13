package pt.saudemin.hds.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import pt.saudemin.hds.dtos.entities.ChoiceAnswerDTO;
import pt.saudemin.hds.entities.ChoiceAnswer;

@Mapper(uses = {AnswerMapper.class, QuestionMapper.class, QuestionnaireMapper.class, InquiryMapper.class, UserMapper.class})
public interface ChoiceAnswerMapper {
    ChoiceAnswerMapper INSTANCE = Mappers.getMapper(ChoiceAnswerMapper.class);
    ChoiceAnswerDTO choiceAnswerToChoiceAnswerDTO(ChoiceAnswer choiceAnswer);
    ChoiceAnswer choiceAnswerDTOToChoiceAnswer(ChoiceAnswerDTO choiceAnswerDTO);
}
