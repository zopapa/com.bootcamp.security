/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.security;

import com.bootcamp.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpRequest;

import java.util.Date;

/**
 *
 * @author edwigegédéon
 */
public class JwtAuthentification {

    static final long EXPIRATIONTIME = 864_000_000; // 10 days
    static final String SECRET = "ThisIsASecret";
    static final String TOKEN_PREFIX = "PAG_";
    static final String HEADER_STRING = "Authorization";

    public static String addAuthentication(User user) {
        String subject = user.getLogin() +"-"+ user.getPassword();
        String JWT = Jwts.builder()
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        //res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
        String token = TOKEN_PREFIX + "" + JWT;

        return token;
    }

    public static String tokenAuthentication(HttpRequest httpRequest) {
        String token = httpRequest.getHeaders().get(HEADER_STRING).toString();
        String userString = null;
        if (token != null) {
            // parse the token.
            userString = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

        }
        return userString;
    }
}

