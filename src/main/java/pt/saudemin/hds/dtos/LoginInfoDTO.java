package pt.saudemin.hds.dtos;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginInfoDTO implements Serializable {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final long serialVersionUID = 1697044427328710913L;

    private String token;
    private Integer personalId;
    private boolean isAdmin;
}
