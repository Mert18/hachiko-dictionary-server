package com.m2t.hachikodictionary.config;

import com.m2t.hachikodictionary.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AccountService accountService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(AccountService accountService, JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationProvider authenticationProvider) {
        this.accountService = accountService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeHttpRequests((auth) -> {
                            try {
                                auth
                                        .requestMatchers("/api/v1/auth/**").permitAll()
                                        .requestMatchers("/api/v1/confirmation/**").permitAll()
                                        .requestMatchers("/api/v1/account/**").hasAnyAuthority("USER")
                                        .requestMatchers("/api/v1/word/create").hasAnyAuthority("ADMIN")
                                        .requestMatchers("/api/v1/word/delete/**").hasAnyAuthority("ADMIN")
                                        .requestMatchers("/api/v1/word/update/**").hasAnyAuthority("ADMIN")
                                        .requestMatchers("/api/v1/word/all").hasAnyAuthority("USER")
                                        .requestMatchers("/api/v1/word/**").permitAll()
                                        .requestMatchers("/api/v1/quote/create").hasAnyAuthority("ADMIN")
                                        .requestMatchers("/api/v1/quote/delete").hasAnyAuthority("ADMIN")
                                        .requestMatchers("/api/v1/quote/**").permitAll()
                                        .requestMatchers("/api/v1/learned-word/**").permitAll()

                                        .anyRequest().authenticated()
                                        .and()
                                        .sessionManagement()
                                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                        .and()
                                        .authenticationProvider(accountService.authenticationProvider())
                                        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }

                );
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
