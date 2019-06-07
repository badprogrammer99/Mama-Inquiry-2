package pt.saudemin.hds.services;

import pt.saudemin.hds.dtos.ChoiceQuestionDTO;
import pt.saudemin.hds.dtos.QuestionDTO;

import java.util.List;

public interface QuestionService {
    List<? extends QuestionDTO> getAll();
    List<QuestionDTO> getAllGenericQuestions();
    List<ChoiceQuestionDTO> getAllChoiceQuestions();
    QuestionDTO getById(long id);
    QuestionDTO create(QuestionDTO questionDTO);
    ChoiceQuestionDTO create(ChoiceQuestionDTO choiceQuestionDTO);
    Boolean delete(long id);
}
