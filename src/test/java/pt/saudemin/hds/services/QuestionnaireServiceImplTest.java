package pt.saudemin.hds.services;

import lombok.var;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pt.saudemin.hds.dtos.QuestionnaireDTO;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class QuestionnaireServiceImplTest {

    @Autowired
    private QuestionnaireService questionnaireService;

    @Test
    public void testGetQuestionnaireById() {
        var id = 1L;

        var questionnaire = questionnaireService.getById(id);

        Assert.assertNotNull(questionnaire);
        Assert.assertEquals(id, (long) questionnaire.getId());
    }

    @Test
    public void testCreateQuestionnaire() {
        var questionnaireDTO = new QuestionnaireDTO(null, "Questionário nhónhó", null);
        var createdQuestionnaire = questionnaireService.create(questionnaireDTO);

        Assert.assertNotNull(createdQuestionnaire);
        Assert.assertEquals(questionnaireDTO.getName(), createdQuestionnaire.getName());
        Assert.assertEquals(questionnaireDTO.getQuestions(), createdQuestionnaire.getQuestions());
    }

    @Test
    public void testUpdateQuestionnaire() {
        var questionnaireDTO = new QuestionnaireDTO(1L, "Questionário nhónhó", null);
        var createQuestionnaire = questionnaireService.update(questionnaireDTO);
        var createdQuestionnaire = questionnaireService.getById(1L);

        Assert.assertNotNull(createQuestionnaire);
        Assert.assertEquals(questionnaireDTO.getId(), createdQuestionnaire.getId());
        Assert.assertEquals(questionnaireDTO.getName(), createdQuestionnaire.getName());
        Assert.assertEquals(questionnaireDTO.getQuestions(), createdQuestionnaire.getQuestions());
    }

    @Test
    public void testDeleteQuestionnaire() {
        var id = 1L;

        var isQuestionnaireDeleted = questionnaireService.delete(id);
        var questionnaire = questionnaireService.getById(id);

        Assert.assertTrue(isQuestionnaireDeleted);
        Assert.assertNull(questionnaire);
    }
}
