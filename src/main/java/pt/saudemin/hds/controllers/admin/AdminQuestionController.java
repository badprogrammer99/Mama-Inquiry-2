package pt.saudemin.hds.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pt.saudemin.hds.controllers.admin.base.BaseAdminController;
import pt.saudemin.hds.dtos.ChoiceQuestionDTO;
import pt.saudemin.hds.dtos.QuestionDTO;
import pt.saudemin.hds.services.QuestionService;

import java.util.List;

@RestController
public class AdminQuestionController extends BaseAdminController {

    @Autowired
    private QuestionService questionService;

    @GetMapping(value = "questions")
    public List<? extends QuestionDTO> getAllQuestions() {
        return questionService.getAll();
    }

    @GetMapping(value = "questions/generic")
    public List<QuestionDTO> getAllGenericQuestions() {
        return questionService.getAllGenericQuestions();
    }

    @GetMapping(value = "questions/choice")
    public List<ChoiceQuestionDTO> getAllChoiceQuestions() {
        return questionService.getAllChoiceQuestions();
    }

    @GetMapping(value = "question/{id}")
    public QuestionDTO getQuestionById(@PathVariable("id") Integer id) {
        return questionService.getById(id);
    }

    @PostMapping(value = "question")
    public QuestionDTO createQuestion(@RequestBody QuestionDTO questionDTO) {
        return questionService.create(questionDTO);
    }

    @PostMapping(value = "question/choice")
    public ChoiceQuestionDTO createChoiceQuestion(@RequestBody ChoiceQuestionDTO choiceQuestionDTO) {
        return questionService.create(choiceQuestionDTO);
    }

    @DeleteMapping(value = "question")
    public Boolean deleteQuestion(@RequestBody int id) {
        return questionService.delete(id);
    }
}
