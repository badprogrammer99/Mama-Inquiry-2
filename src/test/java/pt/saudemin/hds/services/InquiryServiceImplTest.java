package pt.saudemin.hds.services;

import lombok.var;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pt.saudemin.hds.dtos.InquiryDTO;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class InquiryServiceImplTest {

    @Autowired
    private InquiryService inquiryService;

    @Test
    public void testGetInquiryById() {
        var id = 1L;

        var inquiry = inquiryService.getById(id);

        Assert.assertNotNull(inquiry);
        Assert.assertEquals(id, (long) inquiry.getId());
    }

    @Test
    public void testCreateInquiry() {
        var inquiryDTO = new InquiryDTO(null, "Teste Inquérito", null);
        var createdInquiry = inquiryService.create(inquiryDTO);

        Assert.assertNotNull(createdInquiry);
        Assert.assertEquals(inquiryDTO.getName(), createdInquiry.getName());
        Assert.assertEquals(inquiryDTO.getQuestionnaires(), createdInquiry.getQuestionnaires());
    }

    @Test
    public void testUpdateInquiry() {
        var inquiryDTO = new InquiryDTO(1L, "Teste Inquérito", null);
        var updateInquiry = inquiryService.update(inquiryDTO);
        var updatedInquiry = inquiryService.getById(1L);

        Assert.assertNotNull(updateInquiry);
        Assert.assertEquals(inquiryDTO.getId(), updatedInquiry.getId());
        Assert.assertEquals(inquiryDTO.getName(), updatedInquiry.getName());
        Assert.assertEquals(inquiryDTO.getQuestionnaires(), inquiryDTO.getQuestionnaires());
    }

    @Test
    public void testDeleteInquiry() {
        var id = 1L;

        var isInquiryDeleted = inquiryService.delete(id);
        var inquiry = inquiryService.getById(id);

        Assert.assertTrue(isInquiryDeleted);
        Assert.assertNull(inquiry);
    }
}
