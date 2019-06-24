package pt.saudemin.hds.config;

import pt.saudemin.hds.dtos.entities.ChoiceAnswerDTO;
import pt.saudemin.hds.dtos.entities.FreeAnswerDTO;
import pt.saudemin.hds.entities.ChoiceQuestion;
import pt.saudemin.hds.entities.base.Question;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final String SECRET = "VmYq3t6w9z$C&F)J@NcQfTjWnZr4u7x!A%D*G-KaPdSgUkXp2s5v8y/B?E(H+MbQ";
    public static final long EXPIRATION_TIME = 86_400_000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    public static final String GET_GENERIC_QUESTION_PARAM = "generic";
    public static final String GET_CHOICE_QUESTION_PARAM = "choice";

    public static final String ADMIN_PATH = "/admin";
    public static final String USER_PATH = "/user";
    public static final String LOGIN_PATH = "/login";
    public static final String CHANGE_PASSWORD_PATH = "/change-password";

    @SuppressWarnings("serial")
    public static final Map<String, String> ANSWER_QUESTION_MAPPING = new HashMap<String, String>() {{
        put(FreeAnswerDTO.class.getSimpleName(), Question.class.getSimpleName());
        put(ChoiceAnswerDTO.class.getSimpleName(), ChoiceQuestion.class.getSimpleName());
    }};
}
