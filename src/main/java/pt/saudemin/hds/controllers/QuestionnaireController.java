package pt.saudemin.hds.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pt.saudemin.hds.dtos.QuestionnaireDTO;
import pt.saudemin.hds.services.QuestionnaireService;

import java.util.List;

@RestController
public class QuestionnaireController {

    @Autowired
    private QuestionnaireService questionnaireService;

    @RequestMapping(value = "questionnaires", method = RequestMethod.GET)
    public List<QuestionnaireDTO> getAllQuestionaires() {
        return questionnaireService.getAll();
    }

    @RequestMapping(value = "questionnaire/{id}", method = RequestMethod.GET)
    public QuestionnaireDTO getQuestionnaireById(@PathVariable("id") int id) {
        return questionnaireService.getById(id);
    }

    @PostMapping(value = "questionnaire")
    public QuestionnaireDTO createQuestionnaire(@RequestParam QuestionnaireDTO questionnaireDTO) {
        return questionnaireService.create(questionnaireDTO);
    }

    @DeleteMapping(value = "questionnaire")
    public Boolean deleteQuestionnaire(@RequestParam int id) {
        return questionnaireService.delete(id);
    }
}
