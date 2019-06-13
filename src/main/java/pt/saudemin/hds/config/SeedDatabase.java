package pt.saudemin.hds.config;

import lombok.var;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import pt.saudemin.hds.entities.*;
import pt.saudemin.hds.entities.base.Question;
import pt.saudemin.hds.repositories.*;

import java.util.HashSet;

@Configuration
public class SeedDatabase implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InquiryRepository inquiryRepository;

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Autowired
    private AnswerChoiceRepository answerChoiceRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(String... args) throws Exception {
        boolean RUN_SEED = false;

        if (RUN_SEED) {
            var encodedPassword = bCryptPasswordEncoder.encode("123456");

            Inquiry inquiry1 = new Inquiry(null, "Inquérito da Mamã", null, null);
            Inquiry inquiry2 = new Inquiry(null, "Inquérito de Oncologia", null, null);

            inquiryRepository.save(inquiry1);
            inquiryRepository.save(inquiry2);

            User user = new User(null, 170100231, "Bruno Silva", encodedPassword, false, new HashSet<>());

            user.getInquiries().add(inquiry1);
            user.getInquiries().add(inquiry2);

            userRepository.save(new User(null, 170100228, "José Simões", encodedPassword, true, null));
            userRepository.save(new User(null, 170100229, "Ricardo Silva", encodedPassword, true, null));
            userRepository.save(new User(null, 170100230, "Tiago Fereira", encodedPassword, true, null));
            userRepository.save(user);

            Questionnaire questionnaire1 = new Questionnaire(null, "Questionário da qualidade de serviço", inquiry1, null);
            Questionnaire questionnaire2 = new Questionnaire(null, "Questionário do estacionamento", inquiry1, null);
            Questionnaire questionnaire3 = new Questionnaire(null, "Questionário geral", inquiry2, null);

            questionnaireRepository.save(questionnaire1);
            questionnaireRepository.save(questionnaire2);
            questionnaireRepository.save(questionnaire3);

            AnswerChoice answerChoice1 = new AnswerChoice(null, "A");
            AnswerChoice answerChoice2 = new AnswerChoice(null, "B");
            AnswerChoice answerChoice3 = new AnswerChoice(null, "C");
            AnswerChoice answerChoice4 = new AnswerChoice(null, "D");

            answerChoiceRepository.save(answerChoice1);
            answerChoiceRepository.save(answerChoice2);
            answerChoiceRepository.save(answerChoice3);
            answerChoiceRepository.save(answerChoice4);

            Question question1 = new Question(null, "Avaliação dos funcionários", "Avalie os funcionários", questionnaire1);
            Question question2 = new Question(null, "Avaliação do chão", "Avalie a limpeza do chão do estabelecimento.", questionnaire2);
            ChoiceQuestion question3 = ChoiceQuestion.builder()
                    .name("Avaliação do estacionamento")
                    .description("Numa escala de 0 a 10, avalie o estacionamento.")
                    .questionnaire(questionnaire2)
                    .possibleAnswers(1)
                    .answerChoices(new HashSet<>())
                    .build();

            question3.getAnswerChoices().add(answerChoice1);
            question3.getAnswerChoices().add(answerChoice2);
            question3.getAnswerChoices().add(answerChoice3);
            question3.getAnswerChoices().add(answerChoice4);

            questionRepository.save(question1);
            questionRepository.save(question2);
            questionRepository.save(question3);
        }
    }
}
