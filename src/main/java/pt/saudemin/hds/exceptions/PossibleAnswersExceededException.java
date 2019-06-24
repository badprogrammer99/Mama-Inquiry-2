package pt.saudemin.hds.exceptions;

import lombok.NoArgsConstructor;
import pt.saudemin.hds.exceptions.base.AbstractSetUserAnswersException;

@NoArgsConstructor
public class PossibleAnswersExceededException extends AbstractSetUserAnswersException {
    private static final long serialVersionUID = 7711547485153314255L;

    public PossibleAnswersExceededException(String message) {
        super(message);
    }
}
