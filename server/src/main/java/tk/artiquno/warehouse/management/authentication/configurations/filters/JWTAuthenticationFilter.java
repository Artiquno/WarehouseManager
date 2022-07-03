package tk.artiquno.warehouse.management.authentication.configurations.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.Header;
import tk.artiquno.warehouse.management.authentication.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Date;

@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    // TODO: Make these configurable
    public static final String JWT_SECRET = "soverysecret";
    private static final int DAYS_TO_EXPIRE = 10;

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        User credentials;
        try
        {
            credentials = new ObjectMapper().readValue(request.getInputStream(), User.class);
        }
        catch(IOException ex)
        {
            throw new BadCredentialsException("Could not read credentials", ex);
        }

        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.getUsername(),
                        credentials.getPassword(),
                        new ArrayList<>()
                )
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) {
        String token = JWT.create()
                .withSubject(((org.springframework.security.core.userdetails.User)auth.getPrincipal()).getUsername())
                .withExpiresAt(
                        Date.from(Instant.now().plus(Duration.ofDays(DAYS_TO_EXPIRE))))
                .sign(Algorithm.HMAC512(JWT_SECRET.getBytes()));
        response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }
}
