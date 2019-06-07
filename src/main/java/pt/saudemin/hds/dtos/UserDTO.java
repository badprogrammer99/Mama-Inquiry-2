package pt.saudemin.hds.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserDTO implements Serializable {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final static long serialVersionUID = 1945440365211727253L;

    @NotNull(message = "A personal id is required!")
    private Integer personalId;

    @NotBlank(message = "A name is required!")
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "A password is required!")
    private String password;
    private Boolean isAdmin = false;

    private List<InquiryDTO> inquiries;
}
