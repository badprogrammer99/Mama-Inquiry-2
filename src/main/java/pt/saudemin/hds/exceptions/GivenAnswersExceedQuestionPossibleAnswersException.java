package pt.saudemin.hds.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GivenAnswersExceedQuestionPossibleAnswersException extends Exception {
    private static final long serialVersionUID = 7711547485153314255L;

    public GivenAnswersExceedQuestionPossibleAnswersException(String message) {
        super(message);
    }
}
