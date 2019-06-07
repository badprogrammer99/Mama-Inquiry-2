package pt.saudemin.hds.dtos;

import lombok.*;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ChoiceQuestionDTO extends QuestionDTO {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final long serialVersionUID = -3992117254967299922L;

    private Integer possibleAnswers;
    private List<AnswerChoiceDTO> answerChoices;
}
