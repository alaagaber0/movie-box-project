package com.fawry.movie.auth.Controller;


import com.fawry.movie.auth.DTO.Request.LoginRequest;
import com.fawry.movie.auth.DTO.Request.RegisterRequest;
import com.fawry.movie.auth.DTO.Response.AuthenticationResponse;
import com.fawry.movie.auth.Service.UserService;
import com.fawry.movie.dto.response.ApiResponse;
import com.fawry.movie.utils.ErrorCodes;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "auth/")
public class AuthenticationController {
    @Autowired
    private UserService userService;


    @PostMapping(value = "register")
    public ResponseEntity<ApiResponse> register(@RequestBody @Valid RegisterRequest request) {
        userService.register(request);

        ApiResponse response = new ApiResponse(true,
                ErrorCodes.SUCCESS.getMessage(), ErrorCodes.SUCCESS.getCode());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(value = "login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid LoginRequest request) {
        AuthenticationResponse response = userService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
