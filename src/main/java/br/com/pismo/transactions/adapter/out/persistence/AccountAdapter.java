package br.com.pismo.transactions.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.pismo.transactions.adapter.out.persistence.entity.AccountEntity;
import br.com.pismo.transactions.adapter.out.persistence.repository.AccountRepository;
import br.com.pismo.transactions.domain.model.Account;
import br.com.pismo.transactions.domain.model.User;
import br.com.pismo.transactions.port.out.AccountPort;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AccountAdapter implements AccountPort {

    private final AccountRepository accountRepository;

    @Override
    public Account save(User user, Account account) {
        return accountRepository.save(AccountEntity.fromDomain(user, account)).toDomain();
    }

    @Override
    public Optional<Account> findById(Integer accountId) {
        return accountRepository.findById(accountId).map(AccountEntity::toDomain);
    }

    @Override
    public List<Account> findByUser(User user) {
        return AccountEntity.toDomain(accountRepository.findByUserId(user.getId()));
    }

}
