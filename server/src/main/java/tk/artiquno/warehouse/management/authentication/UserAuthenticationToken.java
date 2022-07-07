package tk.artiquno.warehouse.management.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserAuthenticationToken extends AbstractAuthenticationToken {
    private final FullUserDetails principal;

    public UserAuthenticationToken(Collection<? extends GrantedAuthority> authorities, FullUserDetails principal) {
        super(authorities);
        this.principal = principal;

        // I have no idea why this is so important...
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return principal.getPassword();
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
