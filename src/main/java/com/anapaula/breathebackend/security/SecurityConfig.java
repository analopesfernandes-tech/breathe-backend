package com.anapaula.breathebackend.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Value("${ADMIN_USERNAME}")
    private String adminUsername;

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(
            PasswordEncoder passwordEncoder) {

        UserDetails admin = User.builder()
                .username(adminUsername)
                .password(passwordEncoder.encode(adminPassword))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of("http://localhost:5173")
        );

        configuration.setAllowedMethods(
                List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")
        );

        configuration.setAllowedHeaders(
                List.of("*")
        );

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        RequestMatcher apiMatcher = request ->
                request.getRequestURI().startsWith("/api/");

        http
            .cors(cors -> {})

            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(authorize -> authorize
                
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                .requestMatchers(
                    HttpMethod.GET,
                    "/api/messages",
                    "/api/messages/random"
                ).permitAll()

                .requestMatchers(
                    HttpMethod.POST,
                    "/api/messages",
                    "/api/messages/import"
                ).hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.PUT,
                    "/api/messages/**"
                ).hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.DELETE,
                    "/api/messages/**"
                ).hasRole("ADMIN")

                .anyRequest().authenticated()
            )

            .exceptionHandling(exception -> exception
                .defaultAuthenticationEntryPointFor(
                    new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                    apiMatcher
                )
            )

            .formLogin(form -> form
                .permitAll()
            )

            .logout(logout -> logout
                .permitAll()
            );

        return http.build();
    }
}