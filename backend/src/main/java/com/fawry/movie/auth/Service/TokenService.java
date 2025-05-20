package com.fawry.movie.auth.Service;

import com.fawry.movie.auth.Entity.AppUser;
import com.fawry.movie.auth.Entity.Model.Role;
import com.fawry.movie.auth.Entity.Model.SecurityUser;
import com.fawry.movie.auth.Entity.Token;
import com.fawry.movie.auth.Repository.TokenRepository;
import com.fawry.movie.auth.utils.AuthConstants;
import com.fawry.movie.utils.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.ott.InvalidOneTimeTokenException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    public SecretKey getSignKey() {
        try {

            return Keys.hmacShaKeyFor(Base64.getDecoder().decode(AuthConstants.SECRET_KEY));
        }
        catch (Exception exception) {
            throw new AuthenticationServiceException(exception.getMessage());
        }
    }

    public String generateToken(SecurityUser userDetails, boolean isRefreshed) {
        Long expirationLength = AuthConstants.ACCESS_TOKEN_EXPIRATION_LENGTH;
        if (isRefreshed) {
            expirationLength = AuthConstants.REFRESH_TOKEN_EXPIRATION_LENGTH;
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userDetails.getUser().getRole());

        return Jwts
                .builder()
                .claims()
                .add(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationLength))
                .and()
                .signWith(getSignKey())
                .compact();
    }


    public void saveUserToken(AppUser user, String tokenValue) {
        try {
            Token token = new Token();
            token.setToken(tokenValue);
            token.setUser(user);
            tokenRepository.save(token);
        }
        catch (Exception ex) {
            throw new AuthenticationServiceException(ex.getMessage());
        }
    }

    public boolean isValidToken(String token, UserDetails userDetails) {
        String username = extractUserName(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = extractExpirationDate(token);
        return expirationDate.before(new Date());
    }

    public Date extractExpirationDate(String token) {
        if (token == null || token.isBlank())
        {
            throw new InvalidOneTimeTokenException("No such token is found");
        }
        Claims claims = extractAllClaims(token);
        Date exp = claims.getExpiration();
        if (exp != null)
        {
            return exp;
        }
        throw new InvalidOneTimeTokenException("Invalid Token");
    }


    public String extractUserName(String token) {
        if (token == null || token.isBlank())
        {
            throw new InvalidOneTimeTokenException("No such token is found");
        }
        Claims claims = extractAllClaims(token);
        String username = claims.getSubject();
        if (username == null || username.isBlank())
        {
            throw new InvalidOneTimeTokenException("Invalid Token");
        }
        return username;
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public void revokeAllTokens(AppUser user) {
        List<Token> userTokens = tokenRepository.findAllUserTokens(user);
        if (!userTokens.isEmpty())
        {
            userTokens.forEach(token -> tokenRepository.delete(token));
        }
    }

}
