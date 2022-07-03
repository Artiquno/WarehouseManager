package tk.artiquno.warehouse.management.authentication.configurations.filters;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import tk.artiquno.warehouse.management.authentication.services.UserService;

import java.util.List;

/**
 * A {@link BasicAuthenticationFilter} that doesn't trust the authorities
 * in the JWT but always asks the authentication service for the roles.
 *
 * Obvious drawback of this is that it will make a {@link tk.artiquno.warehouse.management.authentication.User}
 * request *on every single request*.
 */
public class ParanoidJWTAuthorizationFilter extends JWTAuthorizationFilter {
    @Autowired
    private UserService userService;

    public ParanoidJWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected List<String> getRoles(DecodedJWT decodedToken) {
        String username = decodedToken.getSubject();
        return userService.getRolesByUsername(username);
    }
}
