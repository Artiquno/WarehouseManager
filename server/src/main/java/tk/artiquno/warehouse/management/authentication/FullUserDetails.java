package tk.artiquno.warehouse.management.authentication;

import lombok.Data;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tk.artiquno.warehouse.management.authentication.mappers.StringToGrantedAuthorityMapper;

import java.util.Collection;
import java.util.List;

@Data
public class FullUserDetails implements UserDetails {
    private Long id;
    private String email;
    private String username;
    private String password;
    private List<String> roles;
    private boolean isActive;

    private static final StringToGrantedAuthorityMapper ROLES_MAPPER = Mappers.getMapper(StringToGrantedAuthorityMapper.class);

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return ROLES_MAPPER.toGrantedAuthority(getRoles());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }
}
