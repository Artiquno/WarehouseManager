package tk.artiquno.warehouse.management.authentication.configurations.filters;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import tk.artiquno.warehouse.management.authentication.FullUserDetails;
import tk.artiquno.warehouse.management.authentication.JWTUtils;
import tk.artiquno.warehouse.management.authentication.UserAuthenticationToken;
import tk.artiquno.warehouse.management.authentication.configurations.SecurityProperties;
import tk.artiquno.warehouse.management.authentication.mappers.StringToGrantedAuthorityMapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private StringToGrantedAuthorityMapper rolesMapper;

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

        Authentication authenticationToken = getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    /**
     * Creates a {@link  UsernamePasswordAuthenticationToken} from the given token
     * @param token The encoded token from the request
     * @return An authentication token, or {@code null} if the token
     * param is invalid
     */
    Authentication getAuthentication(String token) {
        if(token != null)
        {
            DecodedJWT verify;
            try
            {
                verify = JWTUtils.verifyToken(token, securityProperties);
            }
            catch(JWTVerificationException ex)
            {
                return null;
            }

            FullUserDetails details = new FullUserDetails();
            details.setId(verify.getClaim("id").asLong());
            details.setUsername(verify.getSubject());
            details.setRoles(getRoles(verify));
            details.setActive(true);

            if(details.getUsername() != null)
            {
                return new UserAuthenticationToken(rolesMapper.toGrantedAuthority(details.getRoles()), details);
            }
        }

        return null;
    }

    /**
     * Returns the roles from the given {@link DecodedJWT}
     * @param decodedToken The JWT token containing the claims
     *                     for the user being authorized
     * @return The roles that the user has
     */
    protected List<String> getRoles(DecodedJWT decodedToken) {
        return decodedToken.getClaim("roles").asList(String.class);
    }
}
