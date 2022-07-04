package tk.artiquno.warehouse.management.authentication.configurations.filters;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import tk.artiquno.warehouse.management.authentication.JWTUtils;
import tk.artiquno.warehouse.management.authentication.configurations.SecurityProperties;
import tk.artiquno.warehouse.management.authentication.mappers.StringToGrantedAuthorityMapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JWTAuthorizationFilterTest {
    private static final String USERNAME = "username";
    private static final String[] ROLES = {"CLIENT", "WAREHOUSE_ADMIN"};

    private static final String SECRET = "notverysecret";
    private static final String BAD_SECRET = "totallynotsecret";

    @SuppressWarnings("unused")
    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    FilterChain filterChain;

    @Mock
    SecurityContext securityContext;

    @Spy
    SecurityProperties securityProperties = new SecurityProperties();
    @Spy
    SecurityProperties badProperties = new SecurityProperties();

    @Spy
    StringToGrantedAuthorityMapper rolesMapper = Mappers.getMapper(StringToGrantedAuthorityMapper.class);

    @InjectMocks
    JWTAuthorizationFilter authFilter;

    private String goodToken;
    private String badToken;
    private AutoCloseable closeable;

    @BeforeEach
    public void setup() {
        securityProperties.setSecret(SECRET);
        securityProperties.setDuration(Duration.ofMinutes(10));

        badProperties.setSecret(BAD_SECRET);
        badProperties.setDuration(Duration.ofMinutes(10));

        goodToken = JWTUtils.createToken(USERNAME, Arrays.asList(ROLES), securityProperties);
        badToken = JWTUtils.createToken(USERNAME, Arrays.asList(ROLES), badProperties);

        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void cleanup() throws Exception {
        closeable.close();
    }

    @Test
    void getAuthentication() {
        UsernamePasswordAuthenticationToken auth = authFilter.getAuthentication(goodToken);
        String username = (String)auth.getPrincipal();
        assertEquals(USERNAME, username);
        assertIterableEquals(Arrays.asList(ROLES), rolesMapper.toString(auth.getAuthorities()));
    }

    @Test
    void getAuthentication_BadToken() {
        UsernamePasswordAuthenticationToken auth = authFilter.getAuthentication(badToken);
        assertNull(auth);
    }

    @Test
    void doFilterInternal() throws ServletException, IOException {
        when(request.getHeader(HttpHeaders.AUTHORIZATION))
                        .thenReturn(goodToken);

        // When can't set this in the setup method because for some reason
        // SecurityContextHolder.getContext() will return a different
        // SecurityContext mock
        SecurityContextHolder.setContext(securityContext);

        authFilter.doFilterInternal(request, response, filterChain);

        verify(securityContext).setAuthentication(any());
    }

    @Test
    void doFilterInternal_BadToken() throws ServletException, IOException {
        when(request.getHeader(HttpHeaders.AUTHORIZATION))
                .thenReturn("Bad " + badToken);

        SecurityContextHolder.setContext(securityContext);

        authFilter.doFilterInternal(request, response, filterChain);

        verify(securityContext, never()).setAuthentication(any());
    }
}