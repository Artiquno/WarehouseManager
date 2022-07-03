package tk.artiquno.warehouse.management.authentication.configurations.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tk.artiquno.warehouse.management.authentication.User;
import tk.artiquno.warehouse.management.authentication.configurations.SecurityProperties;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Autowired
    private SecurityProperties securityProperties;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

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

        return getAuthenticationManager().authenticate(
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
                        Date.from(Instant.now().plus(Duration.ofDays(securityProperties.getDaysToExpire()))))
                .sign(Algorithm.HMAC512(securityProperties.getSecret()));
        response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }
}
