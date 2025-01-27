package br.com.pismo.transactions.port.out;

import java.util.List;

import br.com.pismo.transactions.domain.model.Transaction;

public interface TransactionPort {

    void save(Transaction transaction);

    List<Transaction> findByAccountId(Integer accountId);
    
}
