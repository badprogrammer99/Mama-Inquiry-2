package pt.saudemin.hds.mappers;

import org.mapstruct.Mapper;

import pt.saudemin.hds.dtos.InquiryDTO;
import pt.saudemin.hds.entities.Inquiry;

@Mapper(uses = {UserMapper.class, QuestionnaireMapper.class})
public interface InquiryMapper {

    InquiryDTO inquiryToInquiryDTO(Inquiry inquiry);
    Inquiry inquiryDTOToInquiry(InquiryDTO inquiryDTO);

}
