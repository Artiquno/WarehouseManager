package tk.artiquno.warehouse.management.authentication.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tk.artiquno.warehouse.management.authentication.configurations.filters.JWTAuthenticationFilter;
import tk.artiquno.warehouse.management.authentication.configurations.filters.JWTAuthorizationFilter;
import tk.artiquno.warehouse.management.authentication.services.AuthenticationUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@ConfigurationPropertiesScan({"tk.artiquno.warehouse.management.authentication.configurations"})
public class SecurityConfiguration {
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static void setupAuthorization(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorize) {
        // Since we're using Swagger to generate our APIs we can't
        // use annotations for authorization...
        // Unless we create our own templates

        authorize
                .antMatchers(HttpMethod.GET, "/orders", "/order/*").hasAnyRole("WAREHOUSE_MANAGER", "CLIENT")

                .antMatchers(HttpMethod.PUT,
                        "/orders/*/decline",
                        "/orders/*/approve",
                        "/orders/*/fulfill").hasRole("WAREHOUSE_MANAGER")
                .antMatchers(HttpMethod.POST, "/orders/schedule-delivery").hasRole("WAREHOUSE_MANAGER")

                .antMatchers(HttpMethod.POST, "/orders").hasRole("CLIENT")
                .antMatchers(HttpMethod.PUT,
                        "/orders",
                        "/orders/*/submit",
                        "/orders/*/cancel").hasRole("CLIENT")

                .antMatchers("/trucks", "/trucks/*").hasRole("WAREHOUSE_MANAGER")

                .antMatchers("/items", "/items/*").hasRole("WAREHOUSE_MANAGER")

                // Allow anyone to create a default user since if you're creating it then no one can log in
                .antMatchers(HttpMethod.POST, "/users/create-default").permitAll()

                .antMatchers("/users", "/users/*").hasRole("SYSTEM_ADMIN")
                .antMatchers("/users/reset-password").authenticated()

                // Allow anyone to view the docs
                .antMatchers("/swagger-ui/*",
                        "/swagger-ui.html",
                        "/v3/api-docs",
                        "/v3/api-docs/*").permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new AuthenticationUserDetailsService();
    }

    @Bean
    public AuthenticationProvider daoAuthProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(daoAuthProvider());
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter(authenticationManager());
    }

    @Bean
    public JWTAuthorizationFilter jwtAuthorizationFilter() {
        return new JWTAuthorizationFilter(authenticationManager());
        // Only enable if paranoid
        // return new ParanoidJWTAuthorizationFilter(authenticationManager());
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200");
            }
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and()
                .authorizeHttpRequests(SecurityConfiguration::setupAuthorization)
                .addFilter(jwtAuthenticationFilter())
                .addFilter(jwtAuthorizationFilter())
                .csrf().disable();
        return http.build();
    }
}
