package br.com.pismo.transactions.domain.mock;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.pismo.transactions.domain.model.Transaction;

public class TransactionMock {

    public static Transaction create(){
        return Transaction.builder()
            .id(1)
            .accountId(1)
            .amount(BigDecimal.TEN)
            .eventDate(LocalDateTime.now())
        .build();
    }
    
}
