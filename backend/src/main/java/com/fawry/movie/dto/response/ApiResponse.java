package com.fawry.movie.dto.response;

public class ApiResponse
{
    private boolean success;
    private String message;
    private String errorCode;

    public ApiResponse(){}

    public ApiResponse(boolean success, String message, String errorCode)
    {
        this.success = success;
        this.message = message;
        this.errorCode = errorCode;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }
}
