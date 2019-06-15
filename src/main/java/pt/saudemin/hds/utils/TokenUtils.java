package pt.saudemin.hds.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import pt.saudemin.hds.config.Constants;
import pt.saudemin.hds.entities.User;

import java.util.Date;

public class TokenUtils {
    public static String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getPersonalId().toString())
                .setExpiration(new Date(System.currentTimeMillis() + Constants.EXPIRATION_TIME))
                .claim("role", user.getIsAdmin() ? "admin" : "user")
                .signWith(Keys.hmacShaKeyFor(Constants.SECRET.getBytes()))
                .compact();
    }

    public static Claims getTokenClaims(String jwt) {
        return Jwts.parser()
                .setSigningKey(Constants.SECRET.getBytes())
                .parseClaimsJws(jwt.replace(Constants.TOKEN_PREFIX, ""))
                .getBody();
    }
}
