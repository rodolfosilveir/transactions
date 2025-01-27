package br.com.pismo.transactions.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.pismo.transactions.adapter.out.persistence.entity.AccountEntity;
import br.com.pismo.transactions.adapter.out.persistence.repository.AccountRepository;
import br.com.pismo.transactions.domain.mock.AccountMock;
import br.com.pismo.transactions.domain.mock.UserMock;
import br.com.pismo.transactions.domain.model.Account;
import br.com.pismo.transactions.domain.model.User;

@ExtendWith(MockitoExtension.class)
class AccountAdapterTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountAdapter accountAdapter;

    @Test
    @DisplayName("should save account successfully")
    void shouldSaveAccountSuccessfully() {
        User userMock = UserMock.create("test_user");
        Account accountMock = AccountMock.create(1, userMock.getId());

        AccountEntity accountEntityMock = AccountEntity.fromDomain(userMock, accountMock);

        when(accountRepository.save(any(AccountEntity.class))).thenReturn(accountEntityMock);

        Account savedAccount = accountAdapter.save(userMock, accountMock);

        assertEquals(accountMock.getAccountNumber(), savedAccount.getAccountNumber());
        verify(accountRepository, times(1)).save(any(AccountEntity.class));
    }

    @Test
    @DisplayName("should return account when found by ID")
    void shouldReturnAccountWhenFoundById() {
        Integer accountId = 1;
        User userMock = UserMock.create("test_user");
        AccountEntity accountEntityMock = AccountEntity.fromDomain(UserMock.create("test_user"), AccountMock.create(1, userMock.getId()));

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(accountEntityMock));

        Optional<Account> account = accountAdapter.findById(accountId);

        assertTrue(account.isPresent());
        verify(accountRepository, times(1)).findById(accountId);
    }

    @Test
    @DisplayName("should return empty Optional when account not found by ID")
    void shouldReturnEmptyOptionalWhenAccountNotFoundById() {
        Integer accountId = 1;

        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        Optional<Account> account = accountAdapter.findById(accountId);

        assertTrue(account.isEmpty());
        verify(accountRepository, times(1)).findById(accountId);
    }

    @Test
    @DisplayName("should return accounts when found by user")
    void shouldReturnAccountsWhenFoundByUser() {
        User userMock = UserMock.create("test_user");
        AccountEntity accountEntityMock = AccountEntity.fromDomain(userMock, AccountMock.create(1, userMock.getId()));
        List<AccountEntity> accountEntityList = Collections.singletonList(accountEntityMock);

        when(accountRepository.findByUserId(userMock.getId())).thenReturn(accountEntityList);

        List<Account> accounts = accountAdapter.findByUser(userMock);

        assertEquals(1, accounts.size());
        verify(accountRepository, times(1)).findByUserId(userMock.getId());
    }

    @Test
    @DisplayName("should return empty list when no accounts found by user")
    void shouldReturnEmptyListWhenNoAccountsFoundByUser() {
        User userMock = UserMock.create("test_user");

        when(accountRepository.findByUserId(userMock.getId())).thenReturn(Collections.emptyList());

        List<Account> accounts = accountAdapter.findByUser(userMock);

        assertTrue(accounts.isEmpty());
        verify(accountRepository, times(1)).findByUserId(userMock.getId());
    }
}

