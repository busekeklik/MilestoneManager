package com.etiya.milestonemanager.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

// LOMBOK
@RequiredArgsConstructor

// SECURITY
@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Value("${spring.security.superadmin.email}")
    private String email;

    @Value("${spring.security.superadmin.password}")
    private String password;

    @Value("${spring.security.superadmin.roles}")
    private String roles;

    // PASSWORD ENCODER
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    } //end PasswordEncoder

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**"))
                .requestMatchers(new AntPathRequestMatcher("/favicon.ico"))
                .requestMatchers(new AntPathRequestMatcher("/html/**"))
                .requestMatchers(new AntPathRequestMatcher("/css/**"))
                .requestMatchers(new AntPathRequestMatcher("/js/**"))
                .requestMatchers(new AntPathRequestMatcher("/img/**"))
                .requestMatchers(new AntPathRequestMatcher("/lib/**"));
    }
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder passwordEncoder) {
        UserDetails admin = User
                .withDefaultPasswordEncoder()
                .username(email)
                .password(password)
                .roles("ROLES_ADMIN")
                .build();

        UserDetails writer = User
                .withDefaultPasswordEncoder()
                .username("writer")
                .password("root")
                .roles("ROLES_ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin, writer);
    }
    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, HandlerMappingIntrospector introspector) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        httpSecurity.authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(new MvcRequestMatcher(introspector, "/")).permitAll()
                                .requestMatchers(new MvcRequestMatcher(introspector, "/index")).permitAll()
                                .requestMatchers(new MvcRequestMatcher(introspector, "/index.html")).permitAll()
                                .requestMatchers(new MvcRequestMatcher(introspector, "/index.htm")).permitAll()
                                .requestMatchers(new MvcRequestMatcher(introspector, "/login")).permitAll()
                                .requestMatchers(new MvcRequestMatcher(introspector, "/admin")).permitAll()
                                .requestMatchers(new MvcRequestMatcher(introspector, "/logout")).permitAll()
                                .requestMatchers(new MvcRequestMatcher(introspector, "//h2-console/**")).permitAll()
                                .anyRequest().authenticated())

                .httpBasic(Customizer.withDefaults())

                .logout().logoutUrl("/logout").invalidateHttpSession(true);
        return httpSecurity.build();
    }

}
