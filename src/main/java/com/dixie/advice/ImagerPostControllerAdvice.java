package com.dixie.advice;

import com.dixie.exception.ImagerPostNotFoundException;
import com.dixie.exception.ImagerPostValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ImagerPostControllerAdvice {

    @ExceptionHandler(ImagerPostNotFoundException.class)
    public ResponseEntity<String> handleImagerPostNotFoundException(ImagerPostNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ImagerPostValidationException.class)
    public ResponseEntity<String> handleImagerPostValidationException(ImagerPostValidationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
