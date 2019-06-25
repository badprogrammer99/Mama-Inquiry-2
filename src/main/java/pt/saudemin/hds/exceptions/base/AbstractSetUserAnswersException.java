package pt.saudemin.hds.exceptions.base;

import lombok.NoArgsConstructor;

/**
 * Base abstract exception for all the set user answers exceptions.
 */
@NoArgsConstructor
public abstract class AbstractSetUserAnswersException extends Exception {
    private static final long serialVersionUID = 1992485174090310504L;

    public AbstractSetUserAnswersException(String message) {
        super(message);
    }
}
