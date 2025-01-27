package br.com.pismo.transactions.domain.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.pismo.transactions.adapter.in.rest.request.CreateTransactionRequest;
import br.com.pismo.transactions.domain.exception.NotFoundException;
import br.com.pismo.transactions.domain.mock.AccountMock;
import br.com.pismo.transactions.domain.mock.OperationTypeMock;
import br.com.pismo.transactions.domain.mock.TransactionMock;
import br.com.pismo.transactions.domain.mock.UserMock;
import br.com.pismo.transactions.domain.model.Account;
import br.com.pismo.transactions.domain.model.OperationType;
import br.com.pismo.transactions.domain.model.Transaction;
import br.com.pismo.transactions.domain.model.User;
import br.com.pismo.transactions.port.in.AccountUC;
import br.com.pismo.transactions.port.out.OperationTypePort;
import br.com.pismo.transactions.port.out.TransactionPort;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private AccountUC accountUC;

    @Mock
    private OperationTypePort operationTypePort;

    @Mock
    private TransactionPort transactionPort;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private TransactionService transactionService;

    private void mockSecurityContext(User user) {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(user);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    @DisplayName("should create transaction successfully")
    void shouldCreateTransactionSuccessfully() {
        User userMock = UserMock.create("test_user");
        mockSecurityContext(userMock);

        Account accountMock = AccountMock.create(1, userMock.getId());
        OperationType operationTypeMock = OperationTypeMock.create(1, "SAQUE");
        CreateTransactionRequest request = new CreateTransactionRequest(1, 1, BigDecimal.valueOf(100));

        when(accountUC.getAccountById(request.getAccountId())).thenReturn(accountMock);
        when(operationTypePort.findById(request.getOperationTypeId())).thenReturn(Optional.of(operationTypeMock));

        transactionService.create(request);

        verify(transactionPort, times(1)).save(any(Transaction.class));
    }

    @Test
    @DisplayName("should throw NotFoundException when account does not belong to user")
    void shouldThrowNotFoundExceptionWhenAccountDoesNotBelongToUser() {
        User userMock = UserMock.create("test_user");
        mockSecurityContext(userMock);

        Account accountMock = AccountMock.create(1, UUID.randomUUID()); 
        CreateTransactionRequest request = new CreateTransactionRequest(1, 1, BigDecimal.valueOf(100));

        when(accountUC.getAccountById(request.getAccountId())).thenReturn(accountMock);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> transactionService.create(request));

        assertEquals("Conta não encontrada", exception.getMessage());
        verify(transactionPort, never()).save(any(Transaction.class));
    }

    @Test
    @DisplayName("should throw NotFoundException when operation type is not found")
    void shouldThrowNotFoundExceptionWhenOperationTypeIsNotFound() {
        User userMock = UserMock.create("test_user");
        mockSecurityContext(userMock);

        Account accountMock = AccountMock.create(1, userMock.getId());
        CreateTransactionRequest request = new CreateTransactionRequest(1, 1, BigDecimal.valueOf(100));

        when(accountUC.getAccountById(request.getAccountId())).thenReturn(accountMock);
        when(operationTypePort.findById(request.getOperationTypeId())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> transactionService.create(request));

        assertEquals("Tipo de operação não encontrada", exception.getMessage());
        verify(transactionPort, never()).save(any(Transaction.class));
    }

    @Test
    @DisplayName("should return transactions with descriptions successfully")
    void shouldReturnTransactionsWithDescriptionsSuccessfully() {
        Integer accountId = 1;

        Account accountMock = AccountMock.create(accountId, UUID.randomUUID());
        List<OperationType> operationTypes = List.of(
            OperationTypeMock.create(1, "SAQUE"),
            OperationTypeMock.create(2, "PAGAMENTO")
        );
        List<Transaction> transactions = List.of(
            TransactionMock.create()
        );

        when(accountUC.getAccountById(accountId)).thenReturn(accountMock);
        when(operationTypePort.findAll()).thenReturn(operationTypes);
        when(transactionPort.findByAccountId(accountId)).thenReturn(transactions);

        List<Transaction> result = transactionService.getTransactions(accountId);

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(accountUC, times(1)).getAccountById(accountId);
        verify(operationTypePort, times(1)).findAll();
        verify(transactionPort, times(1)).findByAccountId(accountId);
    }

    @Test
    @DisplayName("should return empty list when no transactions are found")
    void shouldReturnEmptyListWhenNoTransactionsAreFound() {
        Integer accountId = 1;

        Account accountMock = AccountMock.create(accountId, UUID.randomUUID());

        when(accountUC.getAccountById(accountId)).thenReturn(accountMock);
        when(operationTypePort.findAll()).thenReturn(Collections.emptyList());
        when(transactionPort.findByAccountId(accountId)).thenReturn(Collections.emptyList());

        List<Transaction> result = transactionService.getTransactions(accountId);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(accountUC, times(1)).getAccountById(accountId);
        verify(operationTypePort, times(1)).findAll();
        verify(transactionPort, times(1)).findByAccountId(accountId);
    }
}
