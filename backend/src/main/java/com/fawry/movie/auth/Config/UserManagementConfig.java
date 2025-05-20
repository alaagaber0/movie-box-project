package com.fawry.movie.auth.Config;

import com.fawry.movie.auth.Service.AuthUserDetailsService;
import com.fawry.movie.auth.utils.AuthProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserManagementConfig {
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder(8);
    }

    @Bean
    public UserDetailsService userDetailsService()
    {
        return new AuthUserDetailsService();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(AuthUserDetailsService userDetailsService, PasswordEncoder passwordEncoder)
    {
        return new AuthProvider(userDetailsService, passwordEncoder);
    }
}
