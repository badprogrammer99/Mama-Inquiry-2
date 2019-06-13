package pt.saudemin.hds.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pt.saudemin.hds.controllers.admin.base.BaseAdminController;
import pt.saudemin.hds.dtos.entities.InquiryDTO;
import pt.saudemin.hds.services.InquiryService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class AdminInquiryController extends BaseAdminController {

    @Autowired
    private InquiryService inquiryService;

    @GetMapping(value = "inquiries")
    public ResponseEntity<Object> getAllInquiries() {
        return new ResponseEntity<>(inquiryService.getAll(), HttpStatus.OK);
    }

    @GetMapping(value = "inquiry/{id}")
    public ResponseEntity<Object> getInquiryById(@PathVariable("id") int id) {
       return Optional
               .ofNullable(inquiryService.getById(id))
               .map(inquiry -> new ResponseEntity<Object>(inquiry, HttpStatus.OK))
               .orElseGet(() -> new ResponseEntity<>("No records have been found.", HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "inquiry")
    public ResponseEntity<Object> createInquiry(@RequestBody @Valid InquiryDTO inquiryDTO) {
        return Optional
                .ofNullable(inquiryService.create(inquiryDTO))
                .map(inquiry -> new ResponseEntity<Object>(inquiry, HttpStatus.OK))
                .get();
    }

    @PutMapping(value = "inquiry")
    public ResponseEntity<Object> updateInquiry(@RequestBody @Valid InquiryDTO inquiryDTO) {
        return Optional
                .ofNullable(inquiryService.update(inquiryDTO))
                .map(inquiry -> new ResponseEntity<Object>(inquiry, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("Couldn't update the inquiry, " +
                        "either you didn't supply an ID or the record associated to it doesn't exist. ", HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping(value = "inquiry")
    public ResponseEntity<Object> deleteInquiry(@RequestBody int id) {
        return inquiryService.delete(id) ? new ResponseEntity<>(true, HttpStatus.OK) :
                new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
