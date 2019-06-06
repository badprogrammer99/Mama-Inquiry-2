package pt.saudemin.hds.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Data
public class InquiryDTO implements Serializable {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final static long serialVersionUID = -8491396553123491185L;

    private int id;
    private String name;
    private List<QuestionnaireDTO> questionnaires;
}
