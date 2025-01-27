package br.com.pismo.transactions.adapter.out.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.pismo.transactions.domain.model.Transaction;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "transaction")
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "account_id")
    private Integer accountId;

    @Column(name = "operation_type_id")
    private Integer operationTypeId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    public static TransactionEntity fromDomain(Transaction transaction) {
        return TransactionEntity.builder()
            .accountId(transaction.getAccountId())
            .operationTypeId(transaction.getOperationTypeId())
            .amount(transaction.getAmount())
            .eventDate(transaction.getEventDate())
            .build();
    }

    public Transaction toDomain() {
        return Transaction.builder()
            .id(id)
            .accountId(accountId)
            .operationTypeId(operationTypeId)
            .amount(amount)
            .eventDate(eventDate)
            .build();
    }
    
}
