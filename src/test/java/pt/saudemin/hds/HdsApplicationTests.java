package pt.saudemin.hds;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import pt.saudemin.hds.services.InquiryServiceImplTest;
import pt.saudemin.hds.services.QuestionnaireServiceImplTest;
import pt.saudemin.hds.services.UserServiceImplTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    UserServiceImplTest.class,
    InquiryServiceImplTest.class,
    QuestionnaireServiceImplTest.class
})
public class HdsApplicationTests {

    @Test
    public void contextLoads() {
    }
}
