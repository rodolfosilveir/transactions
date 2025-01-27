package br.com.pismo.transactions.domain.mock;

import java.util.UUID;

import br.com.pismo.transactions.domain.model.Role;
import br.com.pismo.transactions.domain.model.User;

public class UserMock {

    public static User create(String username){
        return User.builder()
            .id(UUID.randomUUID())
            .login(username)
            .password("password")
            .role(Role.ADMIN)
        .build();
    }
    
}
