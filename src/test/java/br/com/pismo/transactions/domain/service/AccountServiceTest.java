package br.com.pismo.transactions.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.pismo.transactions.adapter.in.rest.request.CreateAccountRequest;
import br.com.pismo.transactions.domain.exception.NotFoundException;
import br.com.pismo.transactions.domain.mock.AccountMock;
import br.com.pismo.transactions.domain.mock.UserMock;
import br.com.pismo.transactions.domain.model.Account;
import br.com.pismo.transactions.domain.model.User;
import br.com.pismo.transactions.port.out.AccountPort;
import br.com.pismo.transactions.port.out.UserPort;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private UserPort userPort;

    @Mock
    private AccountPort accountPort;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private AccountService accountService;

    private void mockSecurityContext(User user) {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(user);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    @DisplayName("should create account successfully when user has no document number")
    void shouldCreateAccountSuccessfullyWhenUserHasNoDocumentNumber() {
        User userMock = UserMock.create("test_user");
        userMock.setDocumentNumber(null);
        mockSecurityContext(userMock);

        Account accountMock = AccountMock.create(1, userMock.getId());
        when(accountPort.save(eq(userMock), any(Account.class))).thenReturn(accountMock);

        CreateAccountRequest request = new CreateAccountRequest("12345678900");

        Account account = accountService.create(request);

        assertNotNull(account);
        assertEquals(accountMock.getId(), account.getId());
        verify(userPort, times(1)).save(userMock);
        verify(accountPort, times(1)).save(eq(userMock), any(Account.class));
    }

    @Test
    @DisplayName("should handle DataIntegrityViolationException and retry saving account")
    void shouldHandleDataIntegrityViolationExceptionAndRetrySavingAccount() {
        User userMock = UserMock.create("test_user");
        mockSecurityContext(userMock);

        Account accountMock = AccountMock.create(1, userMock.getId());
        when(accountPort.save(eq(userMock), any(Account.class)))
            .thenThrow(DataIntegrityViolationException.class)
            .thenReturn(accountMock);

        CreateAccountRequest request = new CreateAccountRequest("12345678900");

        Account account = accountService.create(request);

        assertNotNull(account);
        assertEquals(accountMock.getId(), account.getId());
        verify(accountPort, times(2)).save(eq(userMock), any(Account.class));
    }

    @Test
    @DisplayName("should get account by ID successfully")
    void shouldGetAccountByIdSuccessfully() {
        Integer accountId = 1;
        User userMock = UserMock.create("test_user");
        Account accountMock = AccountMock.create(1, userMock.getId());

        when(accountPort.findById(accountId)).thenReturn(Optional.of(accountMock));

        Account account = accountService.getAccountById(accountId);

        assertNotNull(account);
        assertEquals(accountMock.getId(), account.getId());
        verify(accountPort, times(1)).findById(accountId);
    }

    @Test
    @DisplayName("should throw NotFoundException when account not found by ID")
    void shouldThrowNotFoundExceptionWhenAccountNotFoundById() {
        Integer accountId = 1;

        when(accountPort.findById(accountId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> accountService.getAccountById(accountId));

        assertEquals("Conta não encontrada", exception.getMessage());
        verify(accountPort, times(1)).findById(accountId);
    }

    @Test
    @DisplayName("should get user's own accounts successfully")
    void shouldGetOwnAccountsSuccessfully() {
        User userMock = UserMock.create("test_user");
        mockSecurityContext(userMock);

        Account accountMock = AccountMock.create(1, userMock.getId());
        List<Account> accountList = Collections.singletonList(accountMock);

        when(accountPort.findByUser(userMock)).thenReturn(accountList);

        List<Account> accounts = accountService.getOwnAccounts();

        assertNotNull(accounts);
        assertEquals(1, accounts.size());
        assertEquals(accountMock.getId(), accounts.get(0).getId());
        verify(accountPort, times(1)).findByUser(userMock);
    }

    @Test
    @DisplayName("should return empty list when user has no accounts")
    void shouldReturnEmptyListWhenUserHasNoAccounts() {
        User userMock = UserMock.create("test_user");
        mockSecurityContext(userMock);

        when(accountPort.findByUser(userMock)).thenReturn(Collections.emptyList());

        List<Account> accounts = accountService.getOwnAccounts();

        assertNotNull(accounts);
        assertTrue(accounts.isEmpty());
        verify(accountPort, times(1)).findByUser(userMock);
    }
}

