package com.fawry.movie.auth.DTO.Request;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public @NotBlank String getUsername()
    {
        return username;
    }

    public void setUsername(@NotBlank String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(@NotBlank String password)
    {
        this.password = password;
    }
}
