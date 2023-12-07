package co.flexidev.theta.config;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity()
public class SecurityConfig {

    private final YamlConfig yamlConfig;
    private final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    public SecurityConfig(YamlConfig yamlConfig) {
        this.yamlConfig = yamlConfig;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults());
        http.csrf(csrf -> csrf.disable());
        http.sessionManagement(sessionManager  -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(
                (request, response, exception) -> {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());
                }));
        http.authorizeHttpRequests(authorizeHttpRequest -> authorizeHttpRequest
                .requestMatchers("/actuator/health", "/actuator/metrics", "/actuator/metrics/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/api/monitor/**", "/api/authentication/**").permitAll()
                .anyRequest().authenticated()
        );
        if (yamlConfig.getPublicApiList().size() > 0) {
            http.authorizeHttpRequests(authorizeHttpRequest -> authorizeHttpRequest
                    .requestMatchers(String.join(", ", yamlConfig.getPublicApiList())).permitAll()
                    .anyRequest().authenticated()
            );
            LOGGER.info("Added public API: '{}' ", String.join("', '", yamlConfig.getPublicApiList()));
        }
        http.addFilterBefore(new SecurityFilter(), UsernamePasswordAuthenticationFilter.class); // custom protocol Authorization
        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new
                UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(yamlConfig.getCorsAllowedList());
        LOGGER.info("Added CORS allowed patterns: '{}' ", String.join("', '", yamlConfig.getCorsAllowedList()));
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
