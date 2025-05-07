package com.example.demo.config;

import com.example.demo.ExceptionHandling.HandleException;
import com.example.demo.constants.CommonConstants;
import com.example.demo.constants.JWTConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

import static com.example.demo.constants.CommonConstants.EMAIL;
import static com.example.demo.constants.CommonConstants.PASSWORD;
import static com.example.demo.constants.JWTConstants.END_PUBLIC_KEY;
import static com.example.demo.constants.JWTConstants.RSA;
import static com.example.demo.constants.JWTConstants.END_PUBLIC_KEY;

@Configuration
public class JwtConfig {



    @Bean
    public JwtParser jwtParser(){
        return Jwts.parserBuilder().setSigningKey(getRSAPublicKey()).build();
    }

     private RSAPublicKey getRSAPublicKey(){
        try {

            String publicKeyPEM = JWTConstants.PUBLIC_KEY.replace(JWTConstants.BEGIN_PUBLIC_KEY,"")
                    .replace(JWTConstants.END_PUBLIC_KEY,"")
                    .replaceAll("\\s","");

            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyPEM);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory kf = KeyFactory.getInstance(JWTConstants.RSA);
            return (RSAPublicKey)  kf.generatePublic(spec);
        }catch (Exception exception){
            HandleException.throwRuntimeException("Failde to load RSA public key", exception);
        }
        return null;
     }


    public Claims parseToken(String token){
        try{
            return jwtParser().parseClaimsJws(token).getBody();
        }catch (Exception e){
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    public String getUserRole(String token){
        Claims claims = parseToken(token);
        return claims.get(CommonConstants.ROLE,String.class);
    }

    public String getUserEmail(String token){
        Claims claims = parseToken(token);
        return claims.get(CommonConstants.EMAIL,String.class);
    }

    public String getUserPassword(String token){
        Claims claims = parseToken(token);
        return claims.get(CommonConstants.PASSWORD,String.class);
    }

    public boolean isTokenExpired(String token){
        Claims claims = parseToken(token);
        return claims.getExpiration().before(new Date());
    }
}
