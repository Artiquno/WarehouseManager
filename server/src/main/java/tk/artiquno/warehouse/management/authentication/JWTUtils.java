package tk.artiquno.warehouse.management.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import tk.artiquno.warehouse.management.authentication.configurations.SecurityProperties;

import java.time.Instant;
import java.util.Date;

public class JWTUtils {
    /**
     * Create a JWT from the given claims and settings
     * @param userDetails The user details to include in the claims
     * @param securityProperties Security properties to configure the token with
     * @return A JWT ready to be sent to the authenticated user. The result
     * includes the "Bearer " prefix
     */
    public static String createToken(FullUserDetails userDetails, SecurityProperties securityProperties) {
        return "Bearer " + JWT.create()
                .withClaim("id", userDetails.getId())
                .withSubject(userDetails.getUsername())
                .withArrayClaim("roles", userDetails.getRoles().toArray(new String[0]))
                .withExpiresAt(
                        Date.from(Instant.now().plus(securityProperties.getDuration())))
                .sign(Algorithm.HMAC512(securityProperties.getSecret()));
    }

    /**
     * Verify a JWT string
     * @param token The token to be decoded
     * @param securityProperties A {@link SecurityProperties} to read the secret from
     * @return The decoded JWT that contains the claims
     * @throws JWTVerificationException if the token could not be verified.
     * See {@link com.auth0.jwt.JWTVerifier#verify(String)}
     */
    public static DecodedJWT verifyToken(String token, SecurityProperties securityProperties) {
        return JWT.require(Algorithm.HMAC512(securityProperties.getSecret()))
                .build()
                .verify(token.replace("Bearer ", ""));
    }
}
