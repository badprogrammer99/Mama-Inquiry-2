package pt.saudemin.hds.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import pt.saudemin.hds.dtos.entities.FreeAnswerDTO;
import pt.saudemin.hds.entities.FreeAnswer;

@Mapper(uses = {AnswerMapper.class, QuestionMapper.class, QuestionnaireMapper.class, InquiryMapper.class, UserMapper.class})
public interface FreeAnswerMapper {
    FreeAnswerMapper INSTANCE = Mappers.getMapper(FreeAnswerMapper.class);
    FreeAnswerDTO freeAnswerToFreeAnswerDTO(FreeAnswer freeAnswer);
    FreeAnswer freeAnswerDTOToFreeAnswer(FreeAnswerDTO freeAnswerDTO);
}
