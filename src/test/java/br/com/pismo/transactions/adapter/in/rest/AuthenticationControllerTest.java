package br.com.pismo.transactions.adapter.in.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import br.com.pismo.transactions.adapter.in.rest.request.LoginRequestMock;
import br.com.pismo.transactions.adapter.in.rest.request.RegisterRequestMock;
import br.com.pismo.transactions.adapter.in.rest.response.DefaultResponse;
import br.com.pismo.transactions.adapter.in.rest.response.LoginResponse;
import br.com.pismo.transactions.domain.mock.UserMock;
import br.com.pismo.transactions.domain.model.Role;
import br.com.pismo.transactions.domain.service.TokenService;
import br.com.pismo.transactions.port.in.UserUC;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserUC userUC;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @Test
    @DisplayName("Deve retonar o Login com sucesso, metodo login")
    void shouldReturnsLoginSuccessfullyLogin(){
        String login = "login";
        String password = "password";
        String token = "token";
        Authentication authentication = new UsernamePasswordAuthenticationToken(UserMock.create(login), password);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenReturn(authentication);

        when(tokenService.generateToken(any())).thenReturn(token);

        ResponseEntity<DefaultResponse<LoginResponse>> response = authenticationController.login(LoginRequestMock.create(login, password));

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());

        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Deve cadastrar User com sucesso, metodo register")
    void shouldRegisterUserSuccessfullyRegister(){
        String login = "login";

        ResponseEntity<Void> response = authenticationController.register(RegisterRequestMock.create(login, Role.ADMIN.getText()));

        assertNotNull(response);
        assertEquals(201, response.getStatusCode().value());

        assertNull(response.getBody());
    }
    
}
