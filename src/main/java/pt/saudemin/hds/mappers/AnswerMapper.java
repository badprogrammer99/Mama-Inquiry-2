package pt.saudemin.hds.mappers;

import org.mapstruct.factory.Mappers;
import pt.saudemin.hds.dtos.entities.abstracts.AnswerDTO;
import pt.saudemin.hds.entities.base.Answer;

public interface AnswerMapper {
    AnswerMapper INSTANCE = Mappers.getMapper(AnswerMapper.class);
    AnswerDTO answerToAnswerDTO(Answer answer);
    Answer answerDTOToAnswer(AnswerDTO answerDTO);
}
