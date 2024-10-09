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

    // Secret key used to sign and verify JWT tokens using the HS256 algorithm
    private static final SecretKey secretKey = Jwts.SIG.HS256.key().build();

    // Issuer of the token, used to identify the application that generated the JWT
    private static final String Issuer = "Learning_spring_boot";


    public JwtUtils() {}

    // Validates the given JWT token. It returns 'true' if the token is valid and 'false' otherwise
    public static boolean validate(String jwtToken) {
        // Calls the parseToken method to check if the token can be parsed successfully
        return parseToken(jwtToken) != null;
    }

    // Private method to parse the JWT token and extract the claims (payload)
    private static Optional<Claims> parseToken(String jwtToken) {
        // Configures the JWT parser to use the secret key for signature verification
        var jwtParser = Jwts.parser()
                .verifyWith(secretKey)  // Verifies the token's signature using the secret key
                .build();

        try {
            // Attempts to parse the token and return the claims (payload)
            return Optional.of(jwtParser.parseSignedClaims(jwtToken)
                    .getPayload());
        } catch (JwtException e) {
            // If a JWT-related error occurs, logs the error message
            System.out.println("error : " + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Catches any illegal argument exceptions and throws a runtime exception
            throw new RuntimeException(e);
        }
        // Returns an empty Optional if parsing fails
        return Optional.empty();
    }

    // Extracts the username (subject) from the JWT token by parsing its claims
    public static Optional<String> getUserNameFromToken(String jwtToken) {
        // Calls parseToken to extract claims, then retrieves the 'subject' field (username)
        var ClaimsOptional = parseToken(jwtToken);

        // Maps the claims to the subject (username) and returns it as an Optional
        return ClaimsOptional.map(Claims::getSubject);
    }

    // Generates a new JWT token for the given email (user identification)
    public static String generateToken(String email) {

        // Gets the current date and time (issued at time)
        Date curDate = new Date();

        // Sets the token expiration time to 60 minutes from the issued at time
        var exp = DateUtils.addMinutes(curDate, 60);

        // Builds the JWT token with various fields including ID, issuer, subject, etc.
        return Jwts.builder()
                .id(UUID.randomUUID().toString())  // Randomly generated unique token ID
                .issuer(Issuer)  // Sets the token issuer
                .subject(email)  // Sets the token subject (user's email)
                .signWith(secretKey)  // Signs the token using the secret key
                .issuedAt(curDate)  // Sets the issued at time
                .expiration(exp)  // Sets the expiration time
                .compact();  // Compacts the token into its final string form
    }
}