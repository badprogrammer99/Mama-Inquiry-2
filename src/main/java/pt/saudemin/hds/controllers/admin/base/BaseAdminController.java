package pt.saudemin.hds.controllers.admin.base;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.saudemin.hds.config.Constants;

@RestController
@RequestMapping(value = Constants.ADMIN_PATH)
public abstract class BaseAdminController {

}
