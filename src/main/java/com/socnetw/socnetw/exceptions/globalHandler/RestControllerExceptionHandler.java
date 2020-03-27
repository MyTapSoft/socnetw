package com.socnetw.socnetw.exceptions.globalHandler;

import com.socnetw.socnetw.exceptions.UserAlreadyExist;
import com.socnetw.socnetw.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class RestControllerExceptionHandler {

    @ExceptionHandler(UserAlreadyExist.class)
    public ResponseEntity<Object> duplicateExceptionHandler(UserAlreadyExist exc) {
        log.error(exc.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exc.getMessage());
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> notFoundExceptionHandler(NotFoundException exc) {
        log.error(exc.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exc.getMessage());
    }
}
