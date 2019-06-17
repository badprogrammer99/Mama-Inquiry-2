package pt.saudemin.hds.config.filters;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import lombok.var;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import pt.saudemin.hds.config.Constants;
import pt.saudemin.hds.entities.User;
import pt.saudemin.hds.repositories.UserRepository;
import pt.saudemin.hds.services.UserService;
import pt.saudemin.hds.utils.TokenUtils;

import java.util.Arrays;

public class JWTVerifyingFilter extends RolesAuthorizationFilter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SimpleAccountRealm simpleAccountRealm;

    @Autowired
    private Subject subject;

    // Add synchronized modifier because there is a high chance of concurrent modification.
    @Override
    public synchronized boolean isAccessAllowed(ServletRequest request, ServletResponse arg1, Object mappedValue) {
        var httpRequest = (HttpServletRequest) request;

        if (httpRequest.getServletPath().equals(Constants.LOGIN_PATH)) {
            return true;
        }

        var jwt = httpRequest.getHeader(Constants.HEADER_STRING);

        if (jwt == null || !jwt.startsWith(Constants.TOKEN_PREFIX)) {
            return false;
        }

        var claims = TokenUtils.getTokenClaims(jwt);

        var claim = claims.get("role").toString();
        var requiredRoles = (String[]) mappedValue;

       if (Arrays.asList(requiredRoles).contains(claim)) {
           var user = userRepository.findByPersonalId(Integer.valueOf(claims.getSubject())).get();
           var userId = user.getPersonalId().toString();

           if (!simpleAccountRealm.accountExists(userId)) {
               simpleAccountRealm.addAccount(userId, user.getPassword(), claim);
           }

           subject.login(new UsernamePasswordToken(userId, user.getPassword()));

           return true;
       }

       return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest arg0, ServletResponse arg1) {
        var response = (HttpServletResponse) arg1;
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);

        return false;
    }
}