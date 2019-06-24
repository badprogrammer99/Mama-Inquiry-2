package pt.saudemin.hds.exceptions;

import lombok.NoArgsConstructor;
import pt.saudemin.hds.exceptions.base.AbstractSetUserAnswersException;

@NoArgsConstructor
public class NotAssociatedToInquiryException extends AbstractSetUserAnswersException {
    private static final long serialVersionUID = 8539186940076206522L;

    public NotAssociatedToInquiryException(String message) {
        super(message);
    }
}
