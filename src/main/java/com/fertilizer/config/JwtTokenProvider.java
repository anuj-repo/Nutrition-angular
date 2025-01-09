package com.fertilizer.config;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {
	private static final Logger logger = LogManager.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    public String generateToken(long userId,Long jti,Date issuedAt,Date expiration) {

    	Date now = null;
    	Date expiryDate = null;
    	if(issuedAt != null)
    	{
    		now = issuedAt;
    		if(expiration != null)
    		{
    			expiryDate = expiration;
    		}else {
    			expiryDate = new Date(now.getTime() + jwtExpirationInMs);
    		}
    	}
    	else {
    		now = new Date();
    		expiryDate = new Date(now.getTime() + jwtExpirationInMs);
    	}

        return Jwts.builder().setId(jti.toString())
                .setSubject(Long.toString(userId))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }
    
    public Long getIdFromJWT(String token) {
    	Long id = null;
    	try {	
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        id=Long.parseLong(claims.getId());
    	} catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch(ExpiredJwtException ex) {
    		if(ex.getClaims().getId() != null && !ex.getClaims().getId().isEmpty())
    		{
    			id=Long.parseLong(ex.getClaims().getId());
    		}
    	} catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        } 
        return id;
    }

    public String validateToken(String authToken) {
    	String result = "invalid"; 
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            result = "valid";
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
            result = "expired";
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return result;
    }

}
