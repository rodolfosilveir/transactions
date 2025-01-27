package br.com.pismo.transactions.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.pismo.transactions.adapter.in.rest.request.RegisterRequest;
import br.com.pismo.transactions.adapter.in.rest.request.RegisterRequestMock;
import br.com.pismo.transactions.domain.exception.RoleNotFoundException;
import br.com.pismo.transactions.domain.exception.UserAlreadyExistsException;
import br.com.pismo.transactions.domain.mock.UserMock;
import br.com.pismo.transactions.domain.model.Role;
import br.com.pismo.transactions.port.out.UserPort;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserPort userPort;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    @DisplayName("Deve retonar UsernameNotFoundException, metodo loadUserByUsername")
    void shouldReturnsUsernameNotFoundExceptionLoadUserByUsername(){

        String username = "user";
        when(userPort.findByLogin(username)).thenReturn(Optional.empty());

        UsernameNotFoundException e = Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            authenticationService.loadUserByUsername(username);
        });

        assertEquals("Usuario " + username + " não encontrado", e.getMessage());
    }

    @Test
    @DisplayName("Deve retonar UserDetails com sucesso, metodo loadUserByUsername")
    void shouldReturnsUserDetailsSuccessfullyLoadUserByUsername(){

        String username = "user";
        when(userPort.findByLogin(username)).thenReturn(Optional.of(UserMock.create(username)));

        UserDetails user = authenticationService.loadUserByUsername(username);

        assertNotNull(user);
        assertEquals(username, user.getUsername());

    }

    @Test
    @DisplayName("Deve retonar UserAlreadyExistsException, metodo registerUser")
    void shouldReturnsUserAlreadyExistsExceptionRegisterUser(){

        String username = "user";
        RegisterRequest request = RegisterRequestMock.create(username, Role.ADMIN.getText());

        when(userPort.findByLogin(username)).thenReturn(Optional.of(UserMock.create(username)));

        UserAlreadyExistsException e = Assertions.assertThrows(UserAlreadyExistsException.class, () -> {
            authenticationService.registerUser(request);
        });

        assertEquals("Usuário '" + username + "' ja cadastrado", e.getMessage());
    }

    @Test
    @DisplayName("Deve salvar o user, metodo registerUser")
    void shouldSaveUserRegisterUser(){

        String username = "user";
        RegisterRequest request = RegisterRequestMock.create(username, Role.ADMIN.getText());

        when(userPort.findByLogin(username)).thenReturn(Optional.empty());
        doNothing().when(userPort).save(any());

        authenticationService.registerUser(request);

        verify(userPort, times(1)).save(any());
    }

    @Test
    @DisplayName("Deve throw RoleNotFoundException, metodo registerUser")
    void shouldReturnsRoleNotFoundExceptionRegisterUserTest(){

        String username = "user";
        String role = "errorRole";
        RegisterRequest request = RegisterRequestMock.create(username, role);

        when(userPort.findByLogin(username)).thenReturn(Optional.empty());

        RoleNotFoundException e = Assertions.assertThrows(RoleNotFoundException.class, () -> {
            authenticationService.registerUser(request);
        });

        assertEquals("Role '" + role + "' não permitida. Roles permitidas: 'admin', 'manager', 'customer'", e.getMessage());
    }
    
}
