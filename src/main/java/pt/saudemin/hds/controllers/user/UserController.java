package pt.saudemin.hds.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pt.saudemin.hds.controllers.user.base.BaseUserController;
import pt.saudemin.hds.dtos.entities.abstracts.AnswerDTOList;
import pt.saudemin.hds.exceptions.base.AbstractSetUserAnswersException;
import pt.saudemin.hds.services.UserService;

import javax.validation.Valid;

@RestController
public class UserController extends BaseUserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "id")
    public ResponseEntity<Object> getUserId() {
        return new ResponseEntity<>(userService.getCurrentlyAuthenticatedUser().getPersonalId(), HttpStatus.OK);
    }

    @GetMapping(value = "inquiries")
    public ResponseEntity<Object> getUserInformation() {
        return new ResponseEntity<>(userService.getUserInformation(userService.getCurrentlyAuthenticatedUser().getPersonalId()), HttpStatus.OK);
    }

    @PostMapping(value = "answers")
    public ResponseEntity<Object> sendUserAnswers(@RequestBody @Valid AnswerDTOList answers) {
        try {
            return new ResponseEntity<>(userService.setUserAnswersToQuestions(answers), HttpStatus.OK);
        } catch (AbstractSetUserAnswersException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
