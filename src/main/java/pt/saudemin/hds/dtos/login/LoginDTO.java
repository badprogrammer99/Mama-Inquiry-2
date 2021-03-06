package pt.saudemin.hds.dtos.login;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO implements Serializable {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final long serialVersionUID = -4722141431776824834L;

    @NotNull(message = "A personal id is required!")
    private Integer personalId;

    @NotBlank(message = "A password is required!")
    private String password;
}
