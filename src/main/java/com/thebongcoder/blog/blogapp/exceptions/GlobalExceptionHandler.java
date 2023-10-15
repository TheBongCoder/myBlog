package com.thebongcoder.blog.blogapp.exceptions;

import com.thebongcoder.blog.blogapp.utils.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //handle specific exception
    //Global exception

    @Autowired
    ResponseHandler responseHandler;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(webRequest.getDescription(false));
        log.info("Resource not found for Id: {}", exception.getFieldValue());
        return responseHandler.generateResponse(errorDetails, exception.getMessage(), false, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(webRequest.getDescription(false));
        log.error("Error found", exception);
        return responseHandler.generateResponse(errorDetails, exception.getMessage(), false, HttpStatus.BAD_REQUEST);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return responseHandler.generateResponse(errors, "Error found", false, HttpStatus.BAD_REQUEST);
    }


    /*@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        return super.handleMethodArgumentNotValid(ex, headers, status, request);
        *//*Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });*//*



        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return responseHandler.generateResponse(errors, "Error found", false, HttpStatus.BAD_REQUEST);
//        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }*/


}


