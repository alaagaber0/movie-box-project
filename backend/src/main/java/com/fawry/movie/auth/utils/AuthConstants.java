package com.fawry.movie.auth.utils;

import org.springframework.stereotype.Service;

@Service
public class AuthConstants
{
    public static final Long ACCESS_TOKEN_EXPIRATION_LENGTH = 1000L * 60 * 60 * 24;         // 1 day
    public static final Long REFRESH_TOKEN_EXPIRATION_LENGTH = 1000L * 60 * 60 * 24 * 7;   // 7 days
    public static final String SITE_NAME = "localhost:8080";
    public static final String API_PREFIX = "/api/v1/";
    public static final String ACCT_ACTIVATION_LINK = "auth/activate";
    public static final String RESET_PASSWORD_LINK = "profile/reset-password";
    public static final String SECRET_KEY = "KIcNdqRlsLrR5eu90Y2IzhtiIIwCchPDXN6SZUqx0EQ=";
}
