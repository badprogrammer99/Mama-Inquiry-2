package pt.saudemin.hds.services;

import com.itextpdf.text.Document;
import pt.saudemin.hds.dtos.entities.InquiryDTO;

import java.util.List;

public interface InquiryService {
    List<InquiryDTO> getAll();
    InquiryDTO getById(long id);
    InquiryDTO create(InquiryDTO inquiryDTO);
    InquiryDTO update(InquiryDTO inquiryDTO);
    Boolean delete(long id);
    Document generatePdf(long id);
}
