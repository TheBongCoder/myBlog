package com.thebongcoder.blog.blogapp.security;

import com.thebongcoder.blog.blogapp.config.EnvConfig;

import com.thebongcoder.blog.blogapp.exceptions.BlogAPIException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Autowired
    EnvConfig envConfig;

    //generate Token

    public String generateToken(Authentication authentication) {
        String userName = authentication.getName();
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MILLISECOND, Integer.parseInt(envConfig.getJwtExpirationMiSec()));
        Date expiredDate = calendar.getTime();

        String token = Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS512, envConfig.getJwtSecretKey())
                .compact();
        return token;
    }

    //get username from token
    public String getUserNameFromJwt(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(envConfig.getJwtSecretKey())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();// getUsername
    }

    // validate Jwt token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(envConfig.getJwtSecretKey()).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT signature");
        } catch (UnsupportedJwtException e) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        } catch (MalformedJwtException e) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        } catch (ExpiredJwtException e) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "expired JWT token");
        } catch (IllegalArgumentException e) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Jwt claims is empty");
        }
    }
}
