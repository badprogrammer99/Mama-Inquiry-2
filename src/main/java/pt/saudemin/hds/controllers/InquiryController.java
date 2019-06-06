package pt.saudemin.hds.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pt.saudemin.hds.dtos.InquiryDTO;
import pt.saudemin.hds.services.InquiryService;

@RestController
public class InquiryController {

    @Autowired
    private InquiryService inquiryService;

    @RequestMapping(value = "inquiry/{id}", method = RequestMethod.GET)
    public InquiryDTO getInquiryById(@PathVariable("id") int id) {
        return inquiryService.getById(id);
    }
}
