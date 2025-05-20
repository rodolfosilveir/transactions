package br.com.pismo.transactions.adapter.out.persistence.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import br.com.pismo.transactions.domain.model.Account;
import br.com.pismo.transactions.domain.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "account")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "id_user")
    private UUID idUser;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "account_digit")
    private String accountDigit;

    @Column(name = "level")
    private String level;

    @Column(name = "avaliable_credit_limit")
    private BigDecimal avaliableCreditLimit;

    public static AccountEntity fromDomain(User user, Account domain){
        return AccountEntity.builder()
            .idUser(user.getId())
            .accountNumber(domain.getAccountNumber())
            .accountDigit(domain.getAccountDigit())
            .level(domain.getLevel())
            .avaliableCreditLimit(domain.getAvaliableCreditLimit())
        .build();
    }

    public Account toDomain(){
        return Account.builder()
            .id(id)
            .uuidUser(idUser)
            .accountNumber(accountNumber)
            .accountDigit(accountDigit)
            .level(level)
            .avaliableCreditLimit(avaliableCreditLimit)
        .build();
    }

    public static List<Account> toDomain(List<AccountEntity> entities){
        return entities.stream()
            .map(AccountEntity::toDomain)
            .collect(Collectors.toList());
    }
}
