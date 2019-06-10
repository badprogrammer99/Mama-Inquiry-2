package pt.saudemin.hds.dtos;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final static long serialVersionUID = 1945440365211727253L;

    @JsonIgnore
    private Long id;

    @NotNull(message = "A personal id is required!")
    private Integer personalId;

    @NotBlank(message = "A name is required!")
    private String name;
    private Boolean isAdmin = false;

    private List<InquiryDTO> inquiries;
}
