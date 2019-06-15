package pt.saudemin.hds.services;

import pt.saudemin.hds.dtos.entities.ChoiceQuestionDTO;
import pt.saudemin.hds.dtos.entities.QuestionDTO;

import java.util.List;

public interface QuestionService {
    List<? extends QuestionDTO> getAll();
    List<QuestionDTO> getAllGenericQuestions();
    List<ChoiceQuestionDTO> getAllChoiceQuestions();
    <T extends QuestionDTO> T getById(long id);
    QuestionDTO create(QuestionDTO questionDTO);
    ChoiceQuestionDTO create(ChoiceQuestionDTO choiceQuestionDTO);
    QuestionDTO update(QuestionDTO questionDTO);
    ChoiceQuestionDTO update(ChoiceQuestionDTO choiceQuestionDTO);
    Boolean delete(long id);
}
