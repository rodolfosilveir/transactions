package br.com.pismo.transactions.adapter.in.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.pismo.transactions.domain.model.Account;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Generated;

@Generated
@Builder(access = AccessLevel.PRIVATE)
public record CreatedAccountResponse(

    @JsonProperty("id")
    Integer id,

    @JsonProperty("document_number")
    String documentNumber,

    @JsonProperty("account_number")
    String accountNumber,

    @JsonProperty("account_digit")
    String accountDigit,

    @JsonProperty("level")
    String level

) {

    public static CreatedAccountResponse fromDomain(String documentNumber, Account account){
        return CreatedAccountResponse.builder()
            .id(account.getId())
            .documentNumber(documentNumber)
            .accountNumber(account.getAccountNumber())
            .accountDigit(account.getAccountDigit())
            .level(account.getLevel())
            .build();
    }
    
}
