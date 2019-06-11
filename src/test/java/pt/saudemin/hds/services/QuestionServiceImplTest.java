package pt.saudemin.hds.services;

import lombok.var;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pt.saudemin.hds.dtos.ChoiceQuestionDTO;
import pt.saudemin.hds.dtos.QuestionDTO;
import pt.saudemin.hds.entities.ChoiceQuestion;
import pt.saudemin.hds.entities.base.Question;

import javax.transaction.Transactional;
import java.awt.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class QuestionServiceImplTest {

    @Autowired
    private QuestionService questionService;

    @Test
    public void testGetAllGenericQuestions() {
        var genericQuestions = questionService.getAllGenericQuestions();

        Assert.assertTrue(genericQuestions.stream().noneMatch(question -> question instanceof ChoiceQuestionDTO));
    }

    @Test
    public void testGetQuestionById() {
        var id = 1L;
        var question = questionService.getById(id);

        Assert.assertNotNull(question);
        Assert.assertEquals(id, (long) question.getId());
    }

    @Test
    public void testCreateGenericQuestion() {
        var questionDTO = new QuestionDTO(null, "Questão teste", "Descrição questão teste");
        var createdGenericQuestion = questionService.create(questionDTO);
        var getCreatedGenericQuestion = questionService.getById(createdGenericQuestion.getId());

        Assert.assertNotNull(createdGenericQuestion);
        Assert.assertEquals(questionDTO.getName(), createdGenericQuestion.getName());
        Assert.assertEquals(questionDTO.getDescription(), createdGenericQuestion.getDescription());
        Assert.assertFalse(createdGenericQuestion instanceof ChoiceQuestionDTO);

        Assert.assertNotNull(getCreatedGenericQuestion);
        Assert.assertEquals(getCreatedGenericQuestion.getName(), getCreatedGenericQuestion.getName());
        Assert.assertEquals(getCreatedGenericQuestion.getDescription(), getCreatedGenericQuestion.getDescription());
        Assert.assertFalse(getCreatedGenericQuestion instanceof ChoiceQuestionDTO);
    }

    @Test
    public void testCreateChoiceQuestion() {
        var choiceQuestionDTO = new ChoiceQuestionDTO(null, "Questão teste", "Descrição questão teste", 2, null);
        var createdChoiceQuestion = questionService.create(choiceQuestionDTO);

        Assert.assertNotNull(createdChoiceQuestion);
        Assert.assertNotNull(questionService.getById(createdChoiceQuestion.getId()));
        Assert.assertTrue(questionService.getById(createdChoiceQuestion.getId()) instanceof ChoiceQuestionDTO);

        var getCreatedChoiceQuestion = (ChoiceQuestionDTO) questionService.getById(createdChoiceQuestion.getId());

        Assert.assertEquals(choiceQuestionDTO.getName(), createdChoiceQuestion.getName());
        Assert.assertEquals(choiceQuestionDTO.getDescription(), createdChoiceQuestion.getDescription());
        Assert.assertEquals(choiceQuestionDTO.getPossibleAnswers(), createdChoiceQuestion.getPossibleAnswers());
        Assert.assertEquals(choiceQuestionDTO.getAnswerChoices(), createdChoiceQuestion.getAnswerChoices());

        Assert.assertEquals(choiceQuestionDTO.getName(), getCreatedChoiceQuestion.getName());
        Assert.assertEquals(choiceQuestionDTO.getDescription(), getCreatedChoiceQuestion.getDescription());
        Assert.assertEquals(choiceQuestionDTO.getPossibleAnswers(), getCreatedChoiceQuestion.getPossibleAnswers());
        Assert.assertEquals(choiceQuestionDTO.getAnswerChoices(), getCreatedChoiceQuestion.getAnswerChoices());
    }

    @Test
    public void testUpdateGenericQuestion() {
        var questionDTO = new QuestionDTO(1L, "Questão teste", "Descrição questão teste");
        var updatedGenericQuestion = questionService.update(questionDTO);

        Assert.assertNotNull(updatedGenericQuestion);
        Assert.assertNotNull(questionService.getById(updatedGenericQuestion.getId()));
        Assert.assertFalse(questionService.getById(updatedGenericQuestion.getId()) instanceof ChoiceQuestionDTO);

        var getUpdatedGenericQuestion = questionService.getById(updatedGenericQuestion.getId());

        Assert.assertEquals(questionDTO.getId(), updatedGenericQuestion.getId());
        Assert.assertEquals(questionDTO.getName(), updatedGenericQuestion.getName());
        Assert.assertEquals(questionDTO.getDescription(), updatedGenericQuestion.getDescription());

        Assert.assertEquals(questionDTO.getId(), getUpdatedGenericQuestion.getId());
        Assert.assertEquals(questionDTO.getName(), getUpdatedGenericQuestion.getName());
        Assert.assertEquals(questionDTO.getDescription(), getUpdatedGenericQuestion.getDescription());
    }

    @Test
    public void testUpdateChoiceQuestion() {
        var choiceQuestionDTO = new ChoiceQuestionDTO(3L, "Questão teste", "Descrição questão teste", 2, null);
        var updatedChoiceQuestion = questionService.update(choiceQuestionDTO);

        Assert.assertNotNull(updatedChoiceQuestion);
        Assert.assertNotNull(questionService.getById(updatedChoiceQuestion.getId()));
        Assert.assertTrue(questionService.getById(updatedChoiceQuestion.getId()) instanceof ChoiceQuestionDTO);

        var getUpdatedChoiceQuestion = (ChoiceQuestionDTO) questionService.getById(updatedChoiceQuestion.getId());

        Assert.assertEquals(choiceQuestionDTO.getId(), updatedChoiceQuestion.getId());
        Assert.assertEquals(choiceQuestionDTO.getName(), updatedChoiceQuestion.getName());
        Assert.assertEquals(choiceQuestionDTO.getDescription(), updatedChoiceQuestion.getDescription());
        Assert.assertEquals(choiceQuestionDTO.getPossibleAnswers(), updatedChoiceQuestion.getPossibleAnswers());
        Assert.assertEquals(choiceQuestionDTO.getAnswerChoices(), updatedChoiceQuestion.getAnswerChoices());

        Assert.assertEquals(choiceQuestionDTO.getId(), getUpdatedChoiceQuestion.getId());
        Assert.assertEquals(choiceQuestionDTO.getName(), getUpdatedChoiceQuestion.getName());
        Assert.assertEquals(choiceQuestionDTO.getDescription(), getUpdatedChoiceQuestion.getDescription());
        Assert.assertEquals(choiceQuestionDTO.getPossibleAnswers(), getUpdatedChoiceQuestion.getPossibleAnswers());
        Assert.assertEquals(choiceQuestionDTO.getAnswerChoices(), getUpdatedChoiceQuestion.getAnswerChoices());
    }

    @Test
    public void testDeleteQuestion() {
        var id = 2L;

        var isQuestionDeleted = questionService.delete(id);
        var question = questionService.getById(id);

        Assert.assertTrue(isQuestionDeleted);
        Assert.assertNull(question);
    }
}
