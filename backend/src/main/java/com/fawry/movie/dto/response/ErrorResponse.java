package com.fawry.movie.dto.response;

import java.util.HashMap;
import java.util.Map;


public class ErrorResponse extends ApiResponse{
    private Map<String, String> errors = new HashMap<>();

    public ErrorResponse(boolean success, String message, String errorCode, Map<String, String> errors)
    {
        super(success, message, errorCode);
        this.errors = errors;
    }

    public Map<String, String> getErrors()
    {
        return errors;
    }

    public void setErrors(Map<String, String> errors)
    {
        this.errors = errors;
    }
}
