package com.example.Beehyv.CaseStudy.Utils;

import lombok.extern.slf4j.Slf4j;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import org.apache.commons.lang3.time.DateUtils;

@Slf4j
public class JwtUtils {

    private static final SecretKey secretKey = Jwts.SIG.HS256.key().build();
    private static final  String Issuer = "Learning_spring_boot";

    public JwtUtils() {
    }

    public static boolean validate(String jwtToken) {
        return parseToken(jwtToken) != null;
    }

    private static Optional<Claims> parseToken(String jwtToken) {
        var jwtParser = Jwts.parser()
                .verifyWith(secretKey)
                .build();

        try {
            return Optional.of(jwtParser.parseSignedClaims(jwtToken)
                    .getPayload());
        } catch (JwtException e) {
            System.out.println("error : " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public static Optional<String> getUserNameFromToken(String jwtToken) {
        var ClaimsOptional = parseToken(jwtToken);

        return ClaimsOptional.map(Claims::getSubject);

    }

    public static String generateToken(String email) {

        Date curDate = new Date();
        var exp = DateUtils.addMinutes(curDate,60);
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .issuer(Issuer)
                .subject(email)
                .signWith(secretKey)
                .issuedAt(curDate)
                .expiration(exp)
                .compact();
    }
}
