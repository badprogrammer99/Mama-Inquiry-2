package pt.saudemin.hds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import pt.saudemin.hds.entities.Inquiry;
import pt.saudemin.hds.entities.Questionnaire;
import pt.saudemin.hds.entities.User;

import pt.saudemin.hds.repositories.InquiryRepository;
import pt.saudemin.hds.repositories.QuestionnaireRepository;
import pt.saudemin.hds.repositories.UserRepository;

import java.util.HashSet;

@SpringBootApplication
public class HdsApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InquiryRepository inquiryRepository;

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    private static final boolean RUN_SEED = false;

    public static void main(String[] args) {
        SpringApplication.run(HdsApplication.class, args);
    }

    @Override
    public void run(String... args) {
        if (RUN_SEED) {
            User user = new User(null, 170100231, "Bruno Silva", "123456", false, new HashSet<>());

            Inquiry inquiry1 = new Inquiry(null, "Inquérito da Mamã", null, null);
            Inquiry inquiry2 = new Inquiry(null, "Inquérito de Oncologia", null, null);

            inquiryRepository.save(inquiry1);
            inquiryRepository.save(inquiry2);

            Questionnaire questionnaire1 = new Questionnaire(null, "Questionário da qualidade de serviço", inquiry1, null);
            Questionnaire questionnaire2 = new Questionnaire(null, "Questionário do estacionamento", inquiry1, null);
            Questionnaire questionnaire3 = new Questionnaire(null, "Questionário geral", inquiry2, null);

            questionnaireRepository.save(questionnaire1);
            questionnaireRepository.save(questionnaire2);
            questionnaireRepository.save(questionnaire3);

            user.getInquiries().add(inquiry1);
            user.getInquiries().add(inquiry2);

            userRepository.save(new User(null, 170100228, "José Simões", "123456", true, null));
            userRepository.save(new User(null, 170100229, "Ricardo Silva", "123456", true, null));
            userRepository.save(new User(null, 170100230, "Tiago Fereira", "123456", true, null));
            userRepository.save(user);
        }
    }
}
