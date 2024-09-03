package com.turnosrotativos.exceptions;

import com.turnosrotativos.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach(error ->
                errors.append("Mensaje: ").append(error.getDefaultMessage())
        );
        return new ResponseEntity<>(errors.toString().trim(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<ErrorDTO> businessExceptionHandler(BusinessException ex) {
        ErrorDTO error = ErrorDTO.builder().message(ex.getMessage()).build();
        return new ResponseEntity<>(error, ex.getStatus());
    }
}
