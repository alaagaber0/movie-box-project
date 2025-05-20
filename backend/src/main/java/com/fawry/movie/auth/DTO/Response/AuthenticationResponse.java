package com.fawry.movie.auth.DTO.Response;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fawry.movie.dto.response.ApiResponse;

public class AuthenticationResponse extends ApiResponse
{
    @JsonProperty(value = "access_token")
    private String accessToken;

    @JsonProperty(value = "refresh_token")
    private String refreshToken;

    public AuthenticationResponse(){}

    public AuthenticationResponse(boolean success, String message, String errorCode, String accessToken, String refreshToken)
    {
        super(success, message, errorCode);
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
