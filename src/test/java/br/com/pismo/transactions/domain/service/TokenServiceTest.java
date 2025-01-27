package br.com.pismo.transactions.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.pismo.transactions.domain.model.User;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    private String secret = "mySecretKey";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(tokenService, "secret", secret);
    }

    @Test
    @DisplayName("Deve gerar um token válido, metodo generateToken")
    void shouldReturnsGenerateValidTokenSuccessfulyGenerateToken() {
        User user = User.builder().login("testUser").build();

        String token = tokenService.generateToken(user);

        assertNotNull(token);
        assertFalse(token.isEmpty());

        String subject = JWT.require(Algorithm.HMAC256(secret))
                            .withIssuer("auth-api")
                            .build()
                            .verify(token)
                            .getSubject();

        assertEquals("testUser", subject);
    }

    @Test
    @DisplayName("Deve lançar InternalError ao gerar token, metodo generateToken")
    void shouldThrowInternalErrorGenerateToken() {
        User user = User.builder().login("testUser").build();

        ReflectionTestUtils.setField(tokenService, "secret", "");

        InternalError exception = assertThrows(InternalError.class, () -> {
            tokenService.generateToken(user);
        });

        assertEquals("Erro na geração do token", exception.getMessage());
    }

    @Test
    @DisplayName("Deve validar um token válido, metodo validateToken")
    void shouldValidateValidTokenSuccessfulyValidateToken() {
        User user = User.builder().login("testUser").build();

        String token = tokenService.generateToken(user);
        String subject = tokenService.validateToken(token);

        assertEquals("testUser", subject);
    }

    @Test
    @DisplayName("Deve retornar string vazia ao validar um token inválido, metodo validateToken")
    void shouldReturnEmptyStringSuccessfulyValidateToken() {
        String invalidToken = "invalidToken";

        String result = tokenService.validateToken(invalidToken);

        assertEquals("", result);
    }
    
}
