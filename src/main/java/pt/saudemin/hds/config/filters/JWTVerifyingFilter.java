package pt.saudemin.hds.config.filters;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import pt.saudemin.hds.config.Constants;
import pt.saudemin.hds.utils.TokenUtils;

import java.util.Arrays;

public class JWTVerifyingFilter extends RolesAuthorizationFilter {

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse arg1, Object mappedValue) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if (httpRequest.getServletPath().equals(Constants.LOGIN_PATH)) {
            return true;
        }

        String jwt = httpRequest.getHeader(Constants.HEADER_STRING);

        if (jwt == null || !jwt.startsWith(Constants.TOKEN_PREFIX)) {
            return false;
        }

        Claims claims = TokenUtils.getTokenClaims(jwt);

        String claim = claims.get("role").toString();
        String[] requiredRoles = (String[]) mappedValue;

       return Arrays.asList(requiredRoles).contains(claim);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest arg0, ServletResponse arg1) {
        HttpServletResponse response = (HttpServletResponse) arg1;
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        return false;
    }
}