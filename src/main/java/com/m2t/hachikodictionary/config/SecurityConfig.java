package com.m2t.hachikodictionary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/api/word").permitAll().and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/api/word").hasRole("ADMIN").anyRequest().authenticated();
        return http.build();
    }
    @Bean
    public UserDetailsService userDetailsService() {
        PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("{bcrypt}$2a$10$OgkNmUyrvVZd.XGig065HOh7CUFB/rUctFx/glt/YgqEfOWju/FSK")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

}
