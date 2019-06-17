package pt.saudemin.hds.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDTO implements Serializable {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final long serialVersionUID = -8890200902111613086L;

    @NotNull(message = "The personal ID is required!")
    private Integer personalId;

    @NotBlank(message = "The old password is required!")
    private String oldPassword;

    @NotBlank(message = "The new password is required!")
    private String newPassword;
}
