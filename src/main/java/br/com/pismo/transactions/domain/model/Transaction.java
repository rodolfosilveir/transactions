package br.com.pismo.transactions.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@Generated
public class Transaction {

    private Integer id;

    private Integer accountId;

    private Integer operationTypeId;

    @Setter
    private String operationDescription;

    private BigDecimal amount;

    private LocalDateTime eventDate;

    private static final String PAGAMENTO = "PAGAMENTO";

    public static Transaction create(Integer accountId, OperationType operationType, BigDecimal amount) {
        if(operationType.getDescription().equals(PAGAMENTO) && amount.compareTo(BigDecimal.ZERO) < 0) {
            amount = amount.multiply(BigDecimal.valueOf(-1));
        } else if(!operationType.getDescription().equals(PAGAMENTO) && amount.compareTo(BigDecimal.ZERO) > 0){
            amount = amount.negate();
        }
        return Transaction.builder()
                .accountId(accountId)
                .operationTypeId(operationType.getId())
                .amount(amount)
                .eventDate(LocalDateTime.now())
                .build();
    }
    
}
