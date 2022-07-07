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
import tk.artiquno.warehouse.management.authentication.FullUserDetails;
import tk.artiquno.warehouse.management.authentication.JWTUtils;
import tk.artiquno.warehouse.management.authentication.configurations.SecurityProperties;
import tk.artiquno.warehouse.management.authentication.entities.User;
import tk.artiquno.warehouse.management.authentication.mappers.UserDetailsMapper;
import tk.artiquno.warehouse.management.swagger.dto.UserDTO;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private UserDetailsMapper userDetailsMapper;

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
        FullUserDetails userDetails = (FullUserDetails)auth.getPrincipal();

        String token = JWTUtils.createToken(userDetails, securityProperties);
        response.addHeader(HttpHeaders.AUTHORIZATION, token);

        UserDTO user = userDetailsMapper.toDto(userDetails);

        try {
            new ObjectMapper().writeValue(response.getOutputStream(), user);
        } catch (IOException ex) {
            throw new InternalAuthenticationServiceException("Could not write to output stream", ex);
        }
    }
}
