package tk.artiquno.warehouse.management.authentication.configurations.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tk.artiquno.warehouse.management.authentication.JWTUtils;
import tk.artiquno.warehouse.management.authentication.User;
import tk.artiquno.warehouse.management.authentication.configurations.SecurityProperties;
import tk.artiquno.warehouse.management.authentication.dto.SuccessfulAuthenticationDTO;
import tk.artiquno.warehouse.management.authentication.mappers.StringToGrantedAuthorityMapper;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private StringToGrantedAuthorityMapper rolesMapper;

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
                        Collections.emptyList()
                )
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) {
        final String username = ((org.springframework.security.core.userdetails.User)auth.getPrincipal()).getUsername();
        final List<String> roles = rolesMapper.toString(auth.getAuthorities());


        String token = JWTUtils.createToken(username, roles, securityProperties);
        response.addHeader(HttpHeaders.AUTHORIZATION, token);

        SuccessfulAuthenticationDTO authResponse = new SuccessfulAuthenticationDTO();
        authResponse.setUsername(username);
        authResponse.setRoles(roles);

        try {
            new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
        } catch (IOException ex) {
            throw new InternalAuthenticationServiceException("Could not write to output stream", ex);
        }
    }
}
