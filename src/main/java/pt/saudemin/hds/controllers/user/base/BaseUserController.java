package pt.saudemin.hds.controllers.user.base;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.saudemin.hds.config.Constants;

@RestController
@RequestMapping(value = Constants.USER_PATH)
public abstract class BaseUserController {

}
