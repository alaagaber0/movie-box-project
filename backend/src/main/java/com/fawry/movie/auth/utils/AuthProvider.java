package com.fawry.movie.auth.utils;

import com.fawry.movie.auth.Entity.Model.SecurityUser;
import com.fawry.movie.auth.Service.AuthUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;


public class AuthProvider implements AuthenticationProvider
{
    private final AuthUserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthProvider(AuthUserDetailsService userDetailsService, PasswordEncoder passwordEncoder)
    {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        try
        {
            SecurityUser userDetails = (SecurityUser) userDetailsService.loadUserByUsername(authentication.getName());

            if (passwordEncoder.matches(authentication.getCredentials().toString(), userDetails.getPassword()))
            {
                return new UsernamePasswordAuthenticationToken(userDetails, null);
            }
            throw new BadCredentialsException("Bad Credentials");
        }
        catch (Exception ex)
        {
            String detailedMessage =  ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage();
            throw new BadCredentialsException(detailedMessage);
        }
    }

    @Override
    public boolean supports(Class<?> authentication)
    {
        return false;
    }
}
