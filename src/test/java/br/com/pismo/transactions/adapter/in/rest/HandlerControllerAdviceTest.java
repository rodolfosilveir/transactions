package br.com.pismo.transactions.adapter.in.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import br.com.pismo.transactions.adapter.in.rest.response.DefaultResponse;
import br.com.pismo.transactions.domain.exception.NotFoundException;
import br.com.pismo.transactions.domain.exception.UserAlreadyExistsException;

@ExtendWith(MockitoExtension.class)
class HandlerControllerAdviceTest {

    @InjectMocks
    private HandlerControllerAdvice handlerControllerAdvice;

    @Test
    @DisplayName("should return errorResponse, method handleUserAlreadyExistsException")
    void shouldReturnErrorResponseHandleUserAlreadyExistsException() {
        UserAlreadyExistsException ex = new UserAlreadyExistsException("user already exists");

        ResponseEntity<DefaultResponse<Void>> response = handlerControllerAdvice.handleUserAlreadyExistsException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().getHttpStatus());
    }

    @Test
    @DisplayName("should return errorResponse, method handleBadCredentialsException")
    void shouldReturnErrorResponseHandleBadCredentialsException() {
        BadCredentialsException ex = new BadCredentialsException("invalid credentials");

        ResponseEntity<DefaultResponse<Void>> response = handlerControllerAdvice.handleBadCredentialsException(ex);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(403, response.getBody().getHttpStatus());
        assertEquals(List.of("invalid credentials"), response.getBody().getErros());
    }

    @Test
    @DisplayName("should return errorResponse, method handleNotFoundException")
    void shouldReturnErrorResponseHandleNotFoundException() {
        NotFoundException ex = new NotFoundException("resource not found");

        ResponseEntity<DefaultResponse<Void>> response = handlerControllerAdvice.handleNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(404, response.getBody().getHttpStatus());
        assertEquals(List.of("resource not found"), response.getBody().getErros());
    }

    @Test
    @DisplayName("should return errorResponse, method handleDataIntegrityViolationException")
    void shouldReturnErrorResponseHandleDataIntegrityViolationException() {
        DataIntegrityViolationException ex = new DataIntegrityViolationException("data integrity violation");

        ResponseEntity<DefaultResponse<Void>> response = handlerControllerAdvice.handleDataIntegrityViolationException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(500, response.getBody().getHttpStatus());
        assertEquals(List.of("Erro ao tentar criar a conta. Por favor, tente novamente"), response.getBody().getErros());
    }

    @Test
    @DisplayName("should return errorResponse, method handleException")
    void shouldReturnErrorResponseHandleException() {
        Exception ex = new Exception("unexpected error");

        ResponseEntity<DefaultResponse<Void>> response = handlerControllerAdvice.handleException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(500, response.getBody().getHttpStatus());
        assertEquals(List.of("unexpected error"), response.getBody().getErros());
    }

    @Test
    @DisplayName("should return errorResponse, method handleMethodArgumentNotValidException")
    void shouldReturnErrorResponseHandleMethodArgumentNotValidException() {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "objectName");
        bindingResult.addError(new FieldError("objectName", "field1", "field1 is invalid"));
        bindingResult.addError(new FieldError("objectName", "field2", "field2 is required"));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<DefaultResponse<Void>> response = handlerControllerAdvice.handleMethodArgumentNotValidException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().getHttpStatus());
        assertEquals(2, response.getBody().getErros().size());
        assertEquals("field1: field1 is invalid; ", response.getBody().getErros().get(0));
        assertEquals("field2: field2 is required; ", response.getBody().getErros().get(1));
    }
}

