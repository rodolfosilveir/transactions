package br.com.pismo.transactions.adapter.in.rest.response;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.pismo.transactions.domain.model.Account;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Generated;

@Generated
@Builder(access = AccessLevel.PRIVATE)
public record AccountResponse(

    @JsonProperty("id")
    Integer id,

    @JsonProperty("uuid_user")
    UUID uuid,

    @JsonProperty("account_number")
    String accountNumber,

    @JsonProperty("account_digit")
    String accountDigit,

    @JsonProperty("level")
    String level

) {

    public static AccountResponse fromDomain(Account account){
        return AccountResponse.builder()
            .id(account.getId())
            .uuid(account.getUuidUser())
            .accountNumber(account.getAccountNumber())
            .accountDigit(account.getAccountDigit())
            .level(account.getLevel())
            .build();
    }

    public static List<AccountResponse> fromDomain(List<Account> accounts){
        return accounts.stream()
            .map(AccountResponse::fromDomain)
            .collect(Collectors.toList());
    }
    
}
