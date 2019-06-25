package pt.saudemin.hds.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pt.saudemin.hds.config.Constants;
import pt.saudemin.hds.controllers.admin.base.BaseAdminController;
import pt.saudemin.hds.dtos.entities.ChoiceQuestionDTO;
import pt.saudemin.hds.dtos.entities.QuestionDTO;
import pt.saudemin.hds.services.QuestionService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class AdminQuestionController extends BaseAdminController {

    @Autowired
    private QuestionService questionService;

    @GetMapping(value = "questions")
    public ResponseEntity<Object> getAllQuestions() {
        return new ResponseEntity<>(questionService.getAll(), HttpStatus.OK);
    }

    @GetMapping(value = "questions/" + Constants.GET_GENERIC_QUESTION_PARAM)
    public ResponseEntity<Object> getAllGenericQuestions() {
        return new ResponseEntity<>(questionService.getAllGenericQuestions(), HttpStatus.OK);
    }

    @GetMapping(value = "questions/" + Constants.GET_CHOICE_QUESTION_PARAM)
    public ResponseEntity<Object> getAllChoiceQuestions() {
        return new ResponseEntity<>(questionService.getAllChoiceQuestions(), HttpStatus.OK);
    }

    @GetMapping(value = "question/{id}")
    public ResponseEntity<Object> getQuestionById(@PathVariable("id") Integer id) {
        return Optional
                .ofNullable(questionService.getById(id))
                .map(question -> new ResponseEntity<>(question, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("No records have been found.", HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "question")
    public ResponseEntity<Object> createQuestion(@RequestBody @Valid QuestionDTO questionDTO) {
        return Optional
                .ofNullable(questionService.create(questionDTO))
                .map(question -> new ResponseEntity<Object>(question, HttpStatus.OK))
                .get();
    }

    @PostMapping(value = "question/" + Constants.GET_CHOICE_QUESTION_PARAM)
    public ResponseEntity<Object> createChoiceQuestion(@RequestBody @Valid ChoiceQuestionDTO choiceQuestionDTO) {
        return Optional
                .ofNullable(questionService.create(choiceQuestionDTO))
                .map(question -> new ResponseEntity<Object>(question, HttpStatus.OK))
                .get();
    }

    @PutMapping(value = "question")
    public ResponseEntity<Object> updateQuestion(@RequestBody @Valid QuestionDTO questionDTO) {
        return Optional
                .ofNullable(questionService.update(questionDTO))
                .map(question -> new ResponseEntity<Object>(question, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("Couldn't update the question, " +
                        "either you didn't supply an ID or the record associated to it doesn't exist. ", HttpStatus.BAD_REQUEST));
    }

    @PutMapping(value = "question/" + Constants.GET_CHOICE_QUESTION_PARAM)
    public ResponseEntity<Object> updateChoiceQuestion(@RequestBody @Valid ChoiceQuestionDTO choiceQuestionDTO) {
        return Optional
                .ofNullable(questionService.update(choiceQuestionDTO))
                .map(question -> new ResponseEntity<Object>(question, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("Couldn't update the question, " +
                        "either you didn't supply an ID or the record associated to it doesn't exist. ", HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping(value = "question")
    public ResponseEntity<Object> deleteQuestion(@RequestBody int id) {
        return questionService.delete(id) ? new ResponseEntity<>(true, HttpStatus.OK) :
                new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
