package pt.saudemin.hds.dtos;

import java.io.Serializable;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Data
public class UserDTO implements Serializable  {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final static long serialVersionUID = 1945440365211727253L;

    private Long id;
    private Integer personalId;
    private String name;
    private Boolean isAdmin;
    private List<InquiryDTO> inquiries;
}
