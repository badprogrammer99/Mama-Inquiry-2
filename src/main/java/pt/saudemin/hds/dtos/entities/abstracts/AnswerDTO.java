package pt.saudemin.hds.dtos.entities.abstracts;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;
import pt.saudemin.hds.dtos.entities.AnswerIdDTO;
import pt.saudemin.hds.dtos.entities.ChoiceAnswerDTO;
import pt.saudemin.hds.dtos.entities.FreeAnswerDTO;
import pt.saudemin.hds.entities.FreeAnswer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = FreeAnswerDTO.class, name = "freeAnswer"),
    @JsonSubTypes.Type(value = ChoiceAnswerDTO.class, name = "choiceAnswer")
})
public abstract class AnswerDTO implements Serializable {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final long serialVersionUID = 8690772590470745416L;

    @NotNull(message = "An answer ID object containing the question object is required!")
    private AnswerIdDTO answerId;

    private String observations;
}
