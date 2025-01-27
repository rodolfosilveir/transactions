package br.com.pismo.transactions.port.out;

import java.util.List;
import java.util.Optional;

import br.com.pismo.transactions.domain.model.Account;
import br.com.pismo.transactions.domain.model.User;

public interface AccountPort {

    Account save(User user, Account account);

    Optional<Account> findById(Integer accountId);

    List<Account> findByUser(User user);
    
}
