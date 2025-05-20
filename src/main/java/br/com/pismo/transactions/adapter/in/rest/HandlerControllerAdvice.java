package br.com.pismo.transactions.adapter.in.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.pismo.transactions.adapter.in.rest.response.DefaultResponse;
import br.com.pismo.transactions.domain.exception.NotFoundException;
import br.com.pismo.transactions.domain.exception.UnavaliableCreditLimitException;
import br.com.pismo.transactions.domain.exception.UserAlreadyExistsException;
import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@RestControllerAdvice
public class HandlerControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DefaultResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> erros = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            erros.add(String.format("%s: %s; ", fieldName, errorMessage));
        });

        DefaultResponse<Void> response = DefaultResponse.<Void>builder()
            .httpStatus(400)
            .errors(erros)
            .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<DefaultResponse<Void>> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {

        DefaultResponse<Void> response = DefaultResponse.<Void>builder()
            .httpStatus(400)
            .errors(List.of(ex.getMessage()))
            .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<DefaultResponse<Void>> handleBadCredentialsException(BadCredentialsException ex) {

        DefaultResponse<Void> response = DefaultResponse.<Void>builder()
            .httpStatus(403)
            .errors(List.of(ex.getMessage()))
            .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<DefaultResponse<Void>> handleNotFoundException(NotFoundException ex) {

        DefaultResponse<Void> response = DefaultResponse.<Void>builder()
            .httpStatus(404)
            .errors(List.of(ex.getMessage()))
            .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(UnavaliableCreditLimitException.class)
    public ResponseEntity<DefaultResponse<Void>> handleUnavaliableCreditLimitException(UnavaliableCreditLimitException ex) {

        DefaultResponse<Void> response = DefaultResponse.<Void>builder()
            .httpStatus(403)
            .errors(List.of(ex.getMessage()))
            .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<DefaultResponse<Void>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {

        DefaultResponse<Void> response = DefaultResponse.<Void>builder()
            .httpStatus(500)
            .errors(List.of("Erro ao tentar criar a conta. Por favor, tente novamente"))
            .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DefaultResponse<Void>> handleException(Exception ex) {

        DefaultResponse<Void> response = DefaultResponse.<Void>builder()
            .httpStatus(500)
            .errors(List.of(ex.getMessage()))
            .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    
}
