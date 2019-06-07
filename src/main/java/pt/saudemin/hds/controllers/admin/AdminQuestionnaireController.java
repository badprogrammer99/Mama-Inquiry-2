package pt.saudemin.hds.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pt.saudemin.hds.controllers.admin.base.BaseAdminController;
import pt.saudemin.hds.dtos.QuestionnaireDTO;
import pt.saudemin.hds.services.QuestionnaireService;

import java.util.List;

@RestController
public class AdminQuestionnaireController extends BaseAdminController {

    @Autowired
    private QuestionnaireService questionnaireService;

    @GetMapping(value = "questionnaires")
    public List<QuestionnaireDTO> getAllQuestionnaires() {
        return questionnaireService.getAll();
    }

    @GetMapping(value = "questionnaire/{id}")
    public QuestionnaireDTO getQuestionnaireById(@PathVariable("id") int id) {
        return questionnaireService.getById(id);
    }

    @PostMapping(value = "questionnaire")
    public QuestionnaireDTO createQuestionnaire(@RequestBody QuestionnaireDTO questionnaireDTO) {
        return questionnaireService.create(questionnaireDTO);
    }

    @DeleteMapping(value = "questionnaire")
    public Boolean deleteQuestionnaire(@RequestBody int id) {
        return questionnaireService.delete(id);
    }
}
