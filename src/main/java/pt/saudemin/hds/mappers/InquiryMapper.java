package pt.saudemin.hds.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import org.mapstruct.factory.Mappers;
import pt.saudemin.hds.dtos.InquiryDTO;
import pt.saudemin.hds.entities.Inquiry;

@Mapper(uses = {UserMapper.class, QuestionnaireMapper.class})
public interface InquiryMapper {

    InquiryMapper INSTANCE = Mappers.getMapper(InquiryMapper.class);
    InquiryDTO inquiryToInquiryDTO(Inquiry inquiry);

    @Mapping(ignore = true, target = "users")
    Inquiry inquiryDTOToInquiry(InquiryDTO inquiryDTO);

}
