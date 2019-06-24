package pt.saudemin.hds.exceptions;

import lombok.NoArgsConstructor;
import pt.saudemin.hds.exceptions.base.AbstractSetUserAnswersException;

@NoArgsConstructor
public class ErraticInputException extends AbstractSetUserAnswersException {
    private static final long serialVersionUID = 4532513025100225647L;

    public ErraticInputException(String message) {
        super(message);
    }
}
