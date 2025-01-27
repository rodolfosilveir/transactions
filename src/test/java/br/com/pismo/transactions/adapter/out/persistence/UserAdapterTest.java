package br.com.pismo.transactions.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.pismo.transactions.adapter.out.persistence.entity.UserEntity;
import br.com.pismo.transactions.adapter.out.persistence.repository.UserRepository;
import br.com.pismo.transactions.domain.mock.UserMock;
import br.com.pismo.transactions.domain.model.User;

@ExtendWith(MockitoExtension.class)
class UserAdapterTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserAdapter userAdapter;

    @Test
    @DisplayName("should return UserDetails when user is found by login")
    void shouldReturnUserDetailsWhenUserIsFoundByLogin() {
        String login = "test_user";

        UserEntity userEntityMock = UserEntity.fromDomain(UserMock.create(login));
        when(userRepository.findByLogin(login)).thenReturn(Optional.of(userEntityMock));

        Optional<UserDetails> userDetails = userAdapter.findByLogin(login);

        assertTrue(userDetails.isPresent());
        assertEquals(userEntityMock.toDomain().getUsername(), userDetails.get().getUsername());

        verify(userRepository, times(1)).findByLogin(login);
    }

    @Test
    @DisplayName("should return empty Optional when user is not found by login")
    void shouldReturnEmptyOptionalWhenUserIsNotFoundByLogin() {
        String login = "non_existent_user";

        when(userRepository.findByLogin(login)).thenReturn(Optional.empty());

        Optional<UserDetails> userDetails = userAdapter.findByLogin(login);

        assertTrue(userDetails.isEmpty());
        verify(userRepository, times(1)).findByLogin(login);
    }

    @Test
    @DisplayName("should save user successfully")
    void shouldSaveUserSuccessfully() {
        User userMock = UserMock.create("test_user");

        UserEntity userEntityMock = UserEntity.fromDomain(userMock);

        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntityMock);

        userAdapter.save(userMock);

        verify(userRepository, times(1)).save(any(UserEntity.class));
    }
}
