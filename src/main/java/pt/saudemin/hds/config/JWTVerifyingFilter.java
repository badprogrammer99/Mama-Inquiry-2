package pt.saudemin.hds.config;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;

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

        Claims claims = Jwts.parser()
                .setSigningKey(Constants.SECRET.getBytes())
                .parseClaimsJws(jwt.replace(Constants.TOKEN_PREFIX, ""))
                .getBody();

        String claim = claims.get("role").toString();
        String[] requiredRole = (String[]) mappedValue;

        return claim.equals(requiredRole[0]);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest arg0, ServletResponse arg1) {
        HttpServletResponse response = (HttpServletResponse) arg1;
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        return false;
    }
}