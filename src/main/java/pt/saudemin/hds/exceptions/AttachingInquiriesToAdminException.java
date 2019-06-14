package pt.saudemin.hds.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AttachingInquiriesToAdminException extends Exception {
    private static final long serialVersionUID = 6279372102415509223L;

    public AttachingInquiriesToAdminException(String message) {
        super(message);
    }
}
