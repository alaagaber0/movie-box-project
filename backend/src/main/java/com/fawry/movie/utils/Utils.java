package com.fawry.movie.utils;

import com.fawry.movie.auth.Entity.Model.SecurityUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class Utils
{
    public static String getExceptionSpecificMessage(String message)
    {
        if (message.contains("duplicate") || message.contains("unique"))
        {
            return "This value already exists, Please check your input and try again.";
        }
        if (message.contains("violates") || message.contains("constraint"))
        {
            return "Database integrity violation, Please check your input and try again.";
        }
        else
        {
            return "Input Data are Invalid, Please check your input and try again.";
        }
    }

    public static SecurityUser getAuthenticatedUser()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken)
        {
            throw new UsernameNotFoundException("No Authenticated User is found");
        }
        return (SecurityUser) authentication.getPrincipal();
    }
}
