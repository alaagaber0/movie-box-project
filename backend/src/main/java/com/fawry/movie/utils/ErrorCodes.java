package com.fawry.movie.utils;


public enum ErrorCodes
{
    SUCCESS("Operation completed successfully", "SUC000"),
    BAD_CREDENTIALS("Invalid username or password", "ERR401"),
    SERVER_ERROR("An unexpected error occurred", "ERR006"),
    GENERAL_ERROR("Operation failed. Please try again", "ERR007"),
    ITEM_NOT_FOUND("Item not found", "ERR008"),
    DATABASE_ERROR("constraint violated", "ERR009"),
    BAD_REQUEST("Invalid request. One or more parameters are missing or incorrect", "ERR400"),
    INTEGRATION_ERROR("Failed to fetch data from external API", "ERR405"),
    BATCH_ERROR("Some of input can't be completed", "ERR011");


    private final String message;
    private final String code;

    ErrorCodes(String message, String code)
    {
        this.message = message;
        this.code = code;
    }

    public String getMessage()
    {
        return message;
    }

    public String getCode()
    {
        return code;
    }
}
