package com.tarun.movieReviewApp.config;


import com.tarun.movieReviewApp.filter.JwtRequestFilter;
import com.tarun.movieReviewApp.services.CustomUserDetailsService;
import com.tarun.movieReviewApp.utils.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private  final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    public SecurityConfig(CustomUserDetailsService userDetailsService,JwtUtil jwtUtil){
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception{
       JwtRequestFilter jwtRequestFilter = new JwtRequestFilter(jwtUtil,userDetailsService);

        http
                .csrf(csrf-> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/auth/register","/api/auth/login").permitAll()
                                .requestMatchers("/api/reviews/**").authenticated()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }



        @Bean
        public PasswordEncoder passwordEncoder () {

            return new BCryptPasswordEncoder();
        }
    }

