package com.etiya.milestonemanager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {})
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers("/task/api/v1/**").permitAll()
                        .requestMatchers("/task/api/v1/create").permitAll()
                        .requestMatchers("/user/api/v1/**").permitAll()
                        .requestMatchers("/user_task/api/v1/**").permitAll()
                        .requestMatchers("/api/1.0/authenticate").permitAll()
                        .requestMatchers("/project/api/v1/projects/list").permitAll()
                        .requestMatchers("/team_project/api/v1/project/**").permitAll()
                        .requestMatchers("/team/api/v1/find/{id}").permitAll()
                        .requestMatchers("/team/api/v1/list").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        return http.build();
    }
}
