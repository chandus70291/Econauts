package com.econauts.exceptionhandler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.econauts.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ApiResponse<String> handleRuntime(RuntimeException ex) {
        return new ApiResponse<>(false, ex.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> handleGeneral(Exception ex) {
        return new ApiResponse<>(false, "Something went wrong", null);
    }
}
