package pt.saudemin.hds.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotAssociatedToInquiryException extends Exception {
    private static final long serialVersionUID = 8539186940076206522L;

    public NotAssociatedToInquiryException(String message) {
        super(message);
    }
}
