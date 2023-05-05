package com.cyx.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.lang.reflect.Member;
import java.util.Calendar;

public class JwtUtil {
    public static String getToken(Object member,int jwtLimit){
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.SECOND,jwtLimit);

        String token= JWT.create()
                .withAudience(member.toString())
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC256("myKey"));
        return token;
    }

    public static String resolveToken(String token){

        String memberId = JWT.decode(token).getAudience().get(0);
        return memberId;
    }

    public static boolean verify(String token){
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("myKey")).build();
        DecodedJWT verify = jwtVerifier.verify(token);
        return true;


    }
}
