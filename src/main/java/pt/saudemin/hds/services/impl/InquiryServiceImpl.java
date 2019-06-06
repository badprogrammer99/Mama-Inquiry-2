package pt.saudemin.hds.services.impl;

import com.itextpdf.text.Document;

import lombok.extern.slf4j.Slf4j;
import lombok.var;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.saudemin.hds.dtos.InquiryDTO;
import pt.saudemin.hds.mappers.InquiryMapper;
import pt.saudemin.hds.repositories.InquiryRepository;
import pt.saudemin.hds.services.InquiryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InquiryServiceImpl implements InquiryService {

    private InquiryMapper inquiryMapper = Mappers.getMapper(InquiryMapper.class);

    @Autowired
    private InquiryRepository inquiryRepository;

    @Override
    public List<InquiryDTO> getAll() {
        return inquiryRepository.findAll().stream().map(inquiryMapper::inquiryToInquiryDTO).collect(Collectors.toList());
    }

    @Override
    public InquiryDTO getById(long id) {
        var inquiry = inquiryRepository.findById(id);

        return inquiry.map(inquiryMapper::inquiryToInquiryDTO).orElse(null);
    }

    @Override
    public InquiryDTO create(InquiryDTO inquiryDTO) {
        var inquiry = inquiryMapper.inquiryDTOToInquiry(inquiryDTO);
        InquiryDTO createdInquiry = null;

        try {
            createdInquiry = inquiryMapper.inquiryToInquiryDTO(inquiryRepository.save(inquiry));
        } catch (Exception e) {
            log.error("Error creating object of type " + this.getClass().getSimpleName() + ". Exception details: " + e.getMessage());
        }

        return createdInquiry;
    }

    @Override
    public Boolean delete(long id) {
        try {
            inquiryRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error deleting object of type " + this.getClass().getSimpleName() + ". Exception details: " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public Document generatePdf(long id) {
        throw new UnsupportedOperationException("Operation not supported yet.");
    }
}
