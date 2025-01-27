package br.com.pismo.transactions.port.in;

import java.util.List;

import br.com.pismo.transactions.adapter.in.rest.request.CreateAccountRequest;
import br.com.pismo.transactions.domain.model.Account;

public interface AccountUC {

    Account create(CreateAccountRequest request);

    Account getAccountById(Integer accountId);

    List<Account> getOwnAccounts();
    
}
