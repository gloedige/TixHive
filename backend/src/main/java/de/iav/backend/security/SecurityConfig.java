package de.iav.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .authorizeHttpRequests(customizer -> customizer
                                .requestMatchers("/api/auth/login").permitAll()
                                .requestMatchers("/api/auth/logout").permitAll()
                                .requestMatchers("/api/auth/register").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/api/tixhive/tickets/{id}").hasRole(AppUserRole.ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE, "/api/tixhive/tickets/{id}").hasRole(AppUserRole.USER.name())
                                .requestMatchers(HttpMethod.PUT, "/api/tixhive/tickets/status/{id}").hasRole(AppUserRole.DEVELOPER.name())
                                .requestMatchers(HttpMethod.GET, "/api/tixhive/users/**").authenticated()
                                .requestMatchers(HttpMethod.PUT, "/api/tixhive/users/**").authenticated()
                                .requestMatchers("/api/tixhive/tickets/**").authenticated()
                        //.anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable) //Popup anstatt Browseranfrage
                .build();
    }
}
