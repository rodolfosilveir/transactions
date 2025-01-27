package br.com.pismo.transactions.adapter.in.rest.response;

import java.math.BigDecimal;
import java.util.List;

import br.com.pismo.transactions.domain.model.Transaction;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record AccountBalanceResponse(

    BigDecimal balance,

    List<TransactionResponse> transactions
) {

    public static AccountBalanceResponse fromDomain(List<Transaction> transactions) {
        BigDecimal balance = transactions.stream()
            .map(Transaction::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<TransactionResponse> transactionResponses = transactions.stream()
            .map(TransactionResponse::fromDomain)
            .toList();

        return AccountBalanceResponse.builder()
            .balance(balance)
            .transactions(transactionResponses)
            .build();
    }
    
}
