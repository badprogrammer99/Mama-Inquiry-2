package pt.saudemin.hds.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InquiryDTO implements Serializable {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final static long serialVersionUID = -8491396553123491185L;

    private Long id;

    @NotBlank(message = "A inquiry name is required!")
    private String name;

    private List<QuestionnaireDTO> questionnaires;
}
