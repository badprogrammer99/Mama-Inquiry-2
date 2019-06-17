package pt.saudemin.hds.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PossibleAnswersExceededException extends Exception {
    private static final long serialVersionUID = 7711547485153314255L;

    public PossibleAnswersExceededException(String message) {
        super(message);
    }
}
