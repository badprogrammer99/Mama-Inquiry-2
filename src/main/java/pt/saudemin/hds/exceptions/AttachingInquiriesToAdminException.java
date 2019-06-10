package pt.saudemin.hds.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AttachingInquiriesToAdminException extends RuntimeException {
    public AttachingInquiriesToAdminException(String message) {
        super(message);
    }
}
