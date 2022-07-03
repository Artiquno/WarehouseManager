package tk.artiquno.warehouse.management.authentication.mappers;

import org.mapstruct.Mapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class StringToGrantedAuthorityMapper {
    public GrantedAuthority toGrantedAuthority(String role) {
        return new SimpleGrantedAuthority(role);
    }

    public String toString(GrantedAuthority role) {
        return role.getAuthority();
    }

    public abstract List<GrantedAuthority> toGrantedAuthority(Collection<String> roles);

    public abstract List<String> toString(Collection<? extends GrantedAuthority> roles);
}
