package com.app.api.exception;

import com.app.api.dto.response.ExceptionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(IllegalArgumentException.class)
    public ExceptionResponseDto IllegalArgumentException(Exception e){
        return new ExceptionResponseDto(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ExceptionResponseDto HttpMessageNotReadableException(Exception e){
        return new ExceptionResponseDto(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ExceptionResponseDto HttpRequestMethodNotSupportedException(Exception e){
        return new ExceptionResponseDto(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionResponseDto MethodArgumentNotValidException(Exception e){
        return new ExceptionResponseDto(HttpStatus.BAD_REQUEST, e.getMessage());
    }
}
