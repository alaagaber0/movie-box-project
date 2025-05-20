package com.fawry.movie.auth.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class RegisterRequest {
    @NotBlank
    private String username;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,}$",
            message = "Password must be at least 8 characters long, " +
                    "contain at least one uppercase letter, one digit, and one special character")
    private String password;

    private String role;


    public @NotBlank @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,}$",
            message = "Password must be at least 8 characters long, " +
                    "contain at least one uppercase letter, one digit, and one special character") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,}$",
            message = "Password must be at least 8 characters long, " +
                    "contain at least one uppercase letter, one digit, and one special character") String password) {
        this.password = password;
    }

    public @NotBlank String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank String username) {
        this.username = username;
    }

    public @NotBlank String getRole() {
        return role;
    }

    public void setRole(@NotBlank String role) {
        this.role = role;
    }
}
