package pt.saudemin.hds.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import pt.saudemin.hds.dtos.entities.AnswerChoiceDTO;
import pt.saudemin.hds.entities.AnswerChoice;

@Mapper
public interface AnswerChoiceMapper {
    AnswerChoiceMapper INSTANCE = Mappers.getMapper(AnswerChoiceMapper.class);
    AnswerChoiceDTO answerChoiceToAnswerChoiceDTO(AnswerChoice answerChoice);
    AnswerChoice answerChoiceDTOToAnswerChoice(AnswerChoiceDTO answerChoiceDTO);
}
