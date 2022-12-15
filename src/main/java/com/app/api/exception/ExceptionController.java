package com.app.api.exception;

import com.app.api.dto.response.ExceptionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotAuthorizedException.class)
    public ExceptionResponseDto NotAuthorizedException(Exception e){
        return new ExceptionResponseDto(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ExceptionResponseDto NotFoundException(Exception e){
        return new ExceptionResponseDto(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(DuplicateDataException.class)
    public ExceptionResponseDto DuplicateDataException(Exception e){
        return new ExceptionResponseDto(HttpStatus.BAD_REQUEST, e.getMessage());
    }
}
