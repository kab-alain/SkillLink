package kab.auca.skilllink.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
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
                // CSRF Configuration
                .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless APIs

                // Authorization Rules
                .authorizeHttpRequests(authz -> authz
                        // .requestMatchers("/uploads/**").permitAll() // Allow public access to uploads
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Restrict to ADMIN role
                        .requestMatchers("/user/**").authenticated() // Require authentication for /user/** endpoints
                        .anyRequest().permitAll() // Permit all other requests (adjust as necessary)
                )

                // HTTP Basic Authentication
                .httpBasic(Customizer.withDefaults()); // Replace deprecated `httpBasic()`

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCrypt for secure password hashing
    }
}
