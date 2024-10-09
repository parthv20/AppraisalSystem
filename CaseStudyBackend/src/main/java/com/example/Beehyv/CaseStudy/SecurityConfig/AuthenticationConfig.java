package com.example.Beehyv.CaseStudy.SecurityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthenticationConfig {


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        // Retrieves the AuthenticationManager from the passed AuthenticationConfiguration
        return config.getAuthenticationManager();
    }


    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,
                                                         PasswordEncoder passwordEncoder){

        var authenticationProvider = new DaoAuthenticationProvider();

        // Sets the UserDetailsService to use the custom service to load user details from the database
        authenticationProvider.setUserDetailsService(userDetailsService);

        // Sets the password encoder to be used when validating the user's password during authentication
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return authenticationProvider;  // Returns the configured DaoAuthenticationProvider instance
    }


    @Bean
    public PasswordEncoder bCryptPasswordEncoder(){
        // Uses BCrypt, a widely used password-hashing function, for encoding passwords
        return new BCryptPasswordEncoder();
    }
}

