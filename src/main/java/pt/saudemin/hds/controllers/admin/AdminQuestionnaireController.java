package pt.saudemin.hds.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pt.saudemin.hds.controllers.admin.base.BaseAdminController;
import pt.saudemin.hds.dtos.QuestionnaireDTO;
import pt.saudemin.hds.services.QuestionnaireService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class AdminQuestionnaireController extends BaseAdminController {

    @Autowired
    private QuestionnaireService questionnaireService;

    @GetMapping(value = "questionnaires")
    public ResponseEntity<Object> getAllQuestionnaires() {
        return new ResponseEntity<>(questionnaireService.getAll(), HttpStatus.OK);
    }

    @GetMapping(value = "questionnaire/{id}")
    public ResponseEntity<Object> getQuestionnaireById(@PathVariable("id") int id) {
        return Optional
                .ofNullable(questionnaireService.getById(id))
                .map(questionnaire -> new ResponseEntity<Object>(questionnaire, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("No records have been found.", HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "questionnaire")
    public ResponseEntity<Object> createQuestionnaire(@RequestBody @Valid QuestionnaireDTO questionnaireDTO) {
        return Optional
                .ofNullable(questionnaireService.create(questionnaireDTO))
                .map(questionnaire -> new ResponseEntity<Object>(questionnaire, HttpStatus.OK))
                .get();
    }

    @PutMapping(value = "questionnaire")
    public ResponseEntity<Object> updateQuestionnaire(@RequestBody @Valid QuestionnaireDTO questionnaireDTO) {
        return Optional
                .ofNullable(questionnaireService.update(questionnaireDTO))
                .map(questionnaire -> new ResponseEntity<Object>(questionnaire, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("Couldn't update the questionnaire, " +
                        "either you didn't supply an ID or the record associated to it doesn't exist. ", HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping(value = "questionnaire")
    public ResponseEntity<Object> deleteQuestionnaire(@RequestBody int id) {
        return questionnaireService.delete(id) ? new ResponseEntity<>(true, HttpStatus.OK) :
                new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
