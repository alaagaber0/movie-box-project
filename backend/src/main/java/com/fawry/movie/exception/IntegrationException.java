package com.fawry.movie.exception;

public class IntegrationException extends RuntimeException
{
    public IntegrationException() {}

    public IntegrationException(String message)
    {
        super(message);
    }

    public IntegrationException(String message, Throwable cause)
    {
        super(message, cause);
    }


}
