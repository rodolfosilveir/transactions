package br.com.pismo.transactions.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.pismo.transactions.adapter.out.persistence.entity.TransactionEntity;
import br.com.pismo.transactions.adapter.out.persistence.repository.TransactionRepository;
import br.com.pismo.transactions.domain.mock.TransactionMock;
import br.com.pismo.transactions.domain.model.Transaction;

@ExtendWith(MockitoExtension.class)
class TransactionAdapterTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionAdapter transactionAdapter;

    @Test
    @DisplayName("should save transaction successfully")
    void shouldSaveTransactionSuccessfully() {
        Transaction transactionMock = TransactionMock.create();

        TransactionEntity transactionEntityMock = TransactionEntity.fromDomain(transactionMock);

        when(transactionRepository.save(any(TransactionEntity.class))).thenReturn(transactionEntityMock);

        transactionAdapter.save(transactionMock);

        verify(transactionRepository, times(1)).save(any(TransactionEntity.class));
    }

    @Test
    @DisplayName("should return transactions when accountId is found")
    void shouldReturnTransactionsWhenAccountIdIsFound() {
        Integer accountId = 1;

        TransactionEntity transactionEntityMock = TransactionEntity.fromDomain(TransactionMock.create());
        List<TransactionEntity> transactionEntityList = Collections.singletonList(transactionEntityMock);

        when(transactionRepository.findByAccountId(accountId)).thenReturn(transactionEntityList);

        List<Transaction> transactions = transactionAdapter.findByAccountId(accountId);

        assertEquals(1, transactions.size());

        verify(transactionRepository, times(1)).findByAccountId(accountId);
    }

    @Test
    @DisplayName("should return empty list when accountId has no transactions")
    void shouldReturnEmptyListWhenAccountIdHasNoTransactions() {
        Integer accountId = 1;

        when(transactionRepository.findByAccountId(accountId)).thenReturn(Collections.emptyList());

        List<Transaction> transactions = transactionAdapter.findByAccountId(accountId);

        assertEquals(0, transactions.size());
        verify(transactionRepository, times(1)).findByAccountId(accountId);
    }
}
