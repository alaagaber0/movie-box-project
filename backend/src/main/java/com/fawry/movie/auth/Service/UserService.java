package com.fawry.movie.auth.Service;


import com.fawry.movie.auth.DTO.Request.LoginRequest;
import com.fawry.movie.auth.DTO.Request.RegisterRequest;
import com.fawry.movie.auth.DTO.Response.AuthenticationResponse;
import com.fawry.movie.auth.Entity.AppUser;
import com.fawry.movie.auth.Entity.Model.Role;
import com.fawry.movie.auth.Entity.Model.SecurityUser;
import com.fawry.movie.auth.Repository.UserRepository;
import com.fawry.movie.utils.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public void register(RegisterRequest request) {
        if(userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new AuthenticationServiceException("A user with this username already exists");
        }

        String password = passwordEncoder.encode(request.getPassword());
        AppUser user = new AppUser(request.getUsername(), password, Role.valueOf(request.getRole()));
        user = userRepository.save(user);
    }


    public AuthenticationResponse login(LoginRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        authentication = authenticationProvider.authenticate(authentication);
        SecurityUser userDetails = (SecurityUser) authentication.getPrincipal();
        String accessToken = tokenService.generateToken(userDetails, false);
        String refreshToken = tokenService.generateToken(userDetails, true);
        AppUser user = ((SecurityUser) authentication.getPrincipal()).getUser();
        tokenService.revokeAllTokens(user);

        tokenService.saveUserToken(user, accessToken);
        return new AuthenticationResponse(true,
                ErrorCodes.SUCCESS.getMessage(), ErrorCodes.SUCCESS.getCode(), accessToken, refreshToken);
    }




}
