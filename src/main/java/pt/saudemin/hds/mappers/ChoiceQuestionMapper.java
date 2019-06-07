package pt.saudemin.hds.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pt.saudemin.hds.dtos.ChoiceQuestionDTO;
import pt.saudemin.hds.entities.ChoiceQuestion;

@Mapper
public interface ChoiceQuestionMapper {

    ChoiceQuestionMapper INSTANCE = Mappers.getMapper(ChoiceQuestionMapper.class);
    ChoiceQuestionDTO questionToQuestionDTO(ChoiceQuestion question);
    ChoiceQuestion questionDTOToQuestion(ChoiceQuestionDTO questionDTO);
}
