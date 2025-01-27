package br.com.pismo.transactions.adapter.in.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import br.com.pismo.transactions.adapter.in.rest.request.CreateAccountRequest;
import br.com.pismo.transactions.adapter.in.rest.response.AccountResponse;
import br.com.pismo.transactions.adapter.in.rest.response.CreatedAccountResponse;
import br.com.pismo.transactions.adapter.in.rest.response.DefaultResponse;
import br.com.pismo.transactions.domain.mock.AccountMock;
import br.com.pismo.transactions.port.in.AccountUC;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @Mock
    private AccountUC accountUC;

    @InjectMocks
    private AccountController accountController;

    @Test
    @DisplayName("Deve criar uma conta com sucesso, método create")
    void shouldCreateAccountSuccessfully() {
        CreateAccountRequest request = mock(CreateAccountRequest.class);

        when(accountUC.create(request)).thenReturn(AccountMock.create(1, UUID.randomUUID()));

        ResponseEntity<DefaultResponse<CreatedAccountResponse>> response = accountController.create(request);

        assertNotNull(response);
        assertEquals(201, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deve retornar a conta com sucesso, método getAccountById")
    void shouldReturnAccountSuccessfully() {

        Integer accountId = 1;

        when(accountUC.getAccountById(accountId)).thenReturn(AccountMock.create(accountId, UUID.randomUUID()));

        ResponseEntity<DefaultResponse<AccountResponse>> response = accountController.getAccountById(accountId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
    }
    
    @Test
    @DisplayName("Deve retornar a lista de contas com sucesso, método getOwnAccounts")
    void shouldReturnAccountsListSuccessfully() {

        when(accountUC.getOwnAccounts()).thenReturn(List.of(AccountMock.create(1, UUID.randomUUID())));

        ResponseEntity<DefaultResponse<List<AccountResponse>>> response = accountController.getOwnAccounts();

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
    }
}
