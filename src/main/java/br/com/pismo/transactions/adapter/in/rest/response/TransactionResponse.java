package br.com.pismo.transactions.adapter.in.rest.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.pismo.transactions.domain.model.Transaction;

public record TransactionResponse (

    String id,

    String operationDescription,

    BigDecimal amount,

    LocalDateTime eventDate

){
  
    public static TransactionResponse fromDomain(Transaction transaction) {
        return new TransactionResponse(
            transaction.getId().toString(),
            transaction.getOperationDescription(),
            transaction.getAmount(),
            transaction.getEventDate()
        );
    }
}
