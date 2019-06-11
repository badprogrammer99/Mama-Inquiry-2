package pt.saudemin.hds.utils;

import pt.saudemin.hds.dtos.ChoiceQuestionDTO;
import pt.saudemin.hds.dtos.QuestionDTO;
import pt.saudemin.hds.entities.ChoiceQuestion;
import pt.saudemin.hds.entities.base.Question;
import pt.saudemin.hds.mappers.ChoiceQuestionMapper;
import pt.saudemin.hds.mappers.QuestionMapper;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class QuestionUtils {
    public static List<QuestionDTO> filterListOfQuestionsOnPredicate(List<Question> questions, Predicate<? super Question> predicate) {
        return questions.stream()
                .filter(predicate)
                .map(QuestionMapper.INSTANCE::questionToQuestionDTO)
                .collect(Collectors.toList());
    }

    public static List<ChoiceQuestionDTO> filterListOfChoiceQuestionsOnPredicate(List<ChoiceQuestion> choiceQuestions, Predicate<? super ChoiceQuestion> predicate) {
        return choiceQuestions.stream()
                .filter(predicate)
                .map(ChoiceQuestionMapper.INSTANCE::questionToQuestionDTO)
                .collect(Collectors.toList());
    }
}
