package br.com.pismo.transactions.domain.mock;

import java.util.UUID;

import br.com.pismo.transactions.domain.model.Account;

public class AccountMock {
    
    public static Account create(Integer id, UUID uuidUser){
        return Account.builder()
            .id(id)
            .uuidUser(uuidUser)
            .accountNumber("12345")
            .accountDigit("1")
            .level("SILVER")
            .build();
    }
}
