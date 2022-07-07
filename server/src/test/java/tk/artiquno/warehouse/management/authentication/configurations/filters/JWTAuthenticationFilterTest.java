package tk.artiquno.warehouse.management.authentication.configurations.filters;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import tk.artiquno.warehouse.management.authentication.FullUserDetails;
import tk.artiquno.warehouse.management.authentication.JWTUtils;
import tk.artiquno.warehouse.management.authentication.configurations.SecurityProperties;
import tk.artiquno.warehouse.management.authentication.mappers.StringToGrantedAuthorityMapper;
import tk.artiquno.warehouse.management.authentication.mappers.UserDetailsMapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JWTAuthenticationFilterTest {
    private static final String USERNAME = "username";
    private static final String[] ROLES = {"CLIENT", "WAREHOUSE_ADMIN"};

    private static final String SECRET = "notverysecret";

    @SuppressWarnings("unused")
    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    ServletOutputStream responseStream;

    @Mock
    FilterChain filterChain;

    @Spy
    SecurityProperties securityProperties = new SecurityProperties();

    @Spy
    StringToGrantedAuthorityMapper rolesMapper = Mappers.getMapper(StringToGrantedAuthorityMapper.class);

    @Spy
    UserDetailsMapper userDetailsMapper = Mappers.getMapper(UserDetailsMapper.class);

    @InjectMocks
    JWTAuthenticationFilter authFilter;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        securityProperties.setSecret(SECRET);
        securityProperties.setDuration(Duration.ofMinutes(10));

        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void successfulAuthentication() throws IOException {
        List<String> roles = Arrays.asList(ROLES);
        List<GrantedAuthority> authorities = rolesMapper.toGrantedAuthority(roles);
        FullUserDetails principal = new FullUserDetails();
        principal.setId(1L);
        principal.setUsername(USERNAME);
        principal.setRoles(roles);
        Authentication auth = new UsernamePasswordAuthenticationToken(principal, null, authorities);

        when(response.getOutputStream()).thenReturn(responseStream);

        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        authFilter.successfulAuthentication(request, response, filterChain, auth);

        verify(response).addHeader(eq(HttpHeaders.AUTHORIZATION), captor.capture());

        // Check the Authorization header
        String token = captor.getValue();

        assertNotNull(token);
        assertNotEquals("", token);

        DecodedJWT decodedJWT = JWTUtils.verifyToken(token, securityProperties);
        assertEquals(USERNAME, decodedJWT.getSubject());
        assertIterableEquals(roles, decodedJWT.getClaim("roles").asList(String.class));

        // TODO: Check the response body too
    }
}