package pt.saudemin.hds.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pt.saudemin.hds.controllers.admin.base.BaseAdminController;
import pt.saudemin.hds.dtos.InquiryDTO;
import pt.saudemin.hds.services.InquiryService;

import java.util.List;

public class AdminInquiryController extends BaseAdminController {

    @Autowired
    private InquiryService inquiryService;

    @GetMapping(value = "inquiries")
    public List<InquiryDTO> getAllInquiries() {
        return inquiryService.getAll();
    }

    @GetMapping(value = "inquiry/{id}")
    public InquiryDTO getInquiryById(@PathVariable("id") int id) {
        return inquiryService.getById(id);
    }
}
