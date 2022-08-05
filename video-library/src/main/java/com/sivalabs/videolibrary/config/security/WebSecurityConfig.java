package com.sivalabs.videolibrary.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().disable();
        http.headers().frameOptions().sameOrigin();
        http.authorizeRequests()
                .antMatchers(
                        "/webjars/**",
                        "/resources/**",
                        "/static/**",
                        "/js/**",
                        "/css/**",
                        "/images/**",
                        "/favicon.ico",
                        "/h2-console/**")
                .permitAll()
                .antMatchers("/", "/login", "/registration")
                .permitAll()
                .antMatchers("/h2-console/**")
                .permitAll();

        http.formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error")
                .permitAll();

        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll();

        return http.build();
    }
}
