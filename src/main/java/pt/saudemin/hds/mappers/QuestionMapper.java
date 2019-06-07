package pt.saudemin.hds.mappers;

import org.mapstruct.Mapper;

import org.mapstruct.factory.Mappers;

import pt.saudemin.hds.dtos.QuestionDTO;
import pt.saudemin.hds.entities.base.Question;

@Mapper
public interface QuestionMapper {

    QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);
    QuestionDTO questionToQuestionDTO(Question question);
    Question questionDTOToQuestion(QuestionDTO questionDTO);

}
