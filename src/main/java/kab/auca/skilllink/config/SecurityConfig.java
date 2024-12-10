package kab.auca.skilllink.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Disable CSRF protection for simplicity in a REST API
            .authorizeRequests(authz -> 
                authz
                    .anyRequest().permitAll()  // Allow all requests without authentication
            )
            .httpBasic();  // Enable HTTP Basic Authentication (optional, can be omitted if you don't want HTTP basic)

        return http.build(); // Builds and returns the HTTP security configuration
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Password encoder for hashing passwords
    }
}
