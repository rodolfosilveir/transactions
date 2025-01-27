package br.com.pismo.transactions.adapter.in.rest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.pismo.transactions.adapter.in.rest.request.CreateTransactionRequest;
import br.com.pismo.transactions.adapter.in.rest.response.AccountBalanceResponse;
import br.com.pismo.transactions.adapter.in.rest.response.DefaultResponse;
import br.com.pismo.transactions.domain.mock.TransactionMock;
import br.com.pismo.transactions.domain.model.Transaction;
import br.com.pismo.transactions.port.in.TransactionUC;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @Mock
    private TransactionUC transactionUC;

    @InjectMocks
    private TransactionController transactionController;

    @Test
    @DisplayName("Deve criar uma transação com sucesso, método create")
    void shouldCreateTransactionSuccessfully() {
        CreateTransactionRequest request = mock(CreateTransactionRequest.class);

        doNothing().when(transactionUC).create(request);

        ResponseEntity<Void> response = transactionController.create(request);

        assertNotNull(response);
        assertEquals(201, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deve retornar o saldo da conta com sucesso, método getBalanceAccount")
    void shouldReturnAccountBalanceSuccessfully() {
        Integer accountId = 1;

        List<Transaction> transactions = List.of(TransactionMock.create());

        when(transactionUC.getTransactions(accountId)).thenReturn(transactions);

        ResponseEntity<DefaultResponse<AccountBalanceResponse>> response = transactionController.getBalanceAccount(accountId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());

        AccountBalanceResponse accountBalanceResponse = response.getBody().getResultData();
        assertNotNull(accountBalanceResponse);
        assertEquals(BigDecimal.TEN, accountBalanceResponse.balance());
        assertEquals(1, accountBalanceResponse.transactions().size());
    }
}

