package tk.artiquno.warehouse.management.authentication.configurations.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import tk.artiquno.warehouse.management.authentication.configurations.SecurityProperties;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    @Autowired
    private SecurityProperties securityProperties;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(token == null || !token.startsWith("Bearer "))
        {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    /**
     * Creates a {@link  UsernamePasswordAuthenticationToken} from the given token
     * @param token The encoded token from the request
     * @return An authentication token, or {@code null} if the token
     * param is invalid
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if(token != null)
        {
            String user;
            try
            {
                user = JWT.require(Algorithm.HMAC512(securityProperties.getSecret()))
                        .build()
                        .verify(token.replace("Bearer ", ""))
                        .getSubject();
            }
            catch(JWTVerificationException ex)
            {
                return null;
            }

            if(user != null)
            {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>(0));
            }
        }

        return null;
    }
}
