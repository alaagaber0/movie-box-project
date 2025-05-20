package com.fawry.movie.exception;


import com.fawry.movie.dto.response.ErrorResponse;
import com.fawry.movie.utils.ErrorCodes;
import com.fawry.movie.utils.Utils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.NoSuchElementException;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException ex)
    {
        ErrorResponse response = new ErrorResponse(false, ErrorCodes.ITEM_NOT_FOUND.getMessage(),
                ErrorCodes.ITEM_NOT_FOUND.getCode(), null);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request)
    {
        HashMap<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors())
        {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        ErrorResponse response = new ErrorResponse(false, ErrorCodes.BAD_REQUEST.getMessage(),
                ErrorCodes.BAD_REQUEST.getCode(), errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(IntegrationException.class)
    public ResponseEntity<ErrorResponse> handleIntegrationException(IntegrationException ex)
    {

        ErrorResponse response = new ErrorResponse(false, ErrorCodes.INTEGRATION_ERROR.getMessage(),
                ErrorCodes.INTEGRATION_ERROR.getCode(), Collections.singletonMap("detailed Message", ex.getMessage()));

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex)
    {
        String errorMsg = Utils.getExceptionSpecificMessage(ex.getMostSpecificCause().getMessage());
        ErrorResponse response = new ErrorResponse(false, ErrorCodes.DATABASE_ERROR.getMessage(),
                ErrorCodes.DATABASE_ERROR.getCode(), Collections.singletonMap("detailed_message", errorMsg));

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers,
                                                                          HttpStatusCode status,
                                                                          WebRequest request)
    {
        ErrorResponse response = new ErrorResponse(false, ErrorCodes.BAD_REQUEST.getMessage(),
                ErrorCodes.BAD_REQUEST.getCode(), Collections.singletonMap("detailed_message", ex.getMessage()));

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex)
    {
        String detailedMessage =  ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage();
        ErrorResponse response = new ErrorResponse(false, ErrorCodes.BAD_CREDENTIALS.getMessage(),
                ErrorCodes.BAD_CREDENTIALS.getCode(), Collections.singletonMap("detailed Message", detailedMessage));
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex)
    {
        String detailedMessage =  ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage();
        ErrorResponse response = new ErrorResponse(false, ErrorCodes.GENERAL_ERROR.getMessage(),
                ErrorCodes.GENERAL_ERROR.getCode(), Collections.singletonMap("detailed Message", detailedMessage));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}

