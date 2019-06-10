package pt.saudemin.hds.dtos.login;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String token;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer personalId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean isAdmin;
}
