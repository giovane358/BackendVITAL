package com.emergency.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable()) // ok para teste; em produção, reavalie
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()

                        .requestMatchers(HttpMethod.POST, "/role/register").permitAll()


                        .requestMatchers(HttpMethod.POST, "/team/register").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/team/update").permitAll()
                        .requestMatchers(HttpMethod.GET, "/team/list/disabled").permitAll()
                        .requestMatchers(HttpMethod.GET, "/team/list/enable").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/team/delete").permitAll()

                        .requestMatchers(HttpMethod.POST, "/status/register").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/status/update").permitAll()
                        .requestMatchers(HttpMethod.GET, "/status/list").permitAll()
                        .requestMatchers(HttpMethod.GET, "/status/list/disabled").permitAll()
                        .requestMatchers(HttpMethod.GET, "/status/list/enable").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/status/delete").permitAll()

                        .requestMatchers(HttpMethod.POST, "/occurrence/register").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/occurrence/update").permitAll()
                        .requestMatchers(HttpMethod.GET, "/occurrence/list").permitAll()
                        .requestMatchers(HttpMethod.GET, "/occurrence/list/disabled").permitAll()
                        .requestMatchers(HttpMethod.GET, "/occurrence/list/enable").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/occurrence/delete").permitAll()

                        .requestMatchers(HttpMethod.GET, "/logs").permitAll()
                        .requestMatchers(HttpMethod.GET, "/logs/list").permitAll()


                        // Swagger
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/v3/api-docs.yaml",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-ui/index.html",
                                "/webjars/**",
                                "/doc/**"
                        ).permitAll()

                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}