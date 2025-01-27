package br.com.pismo.transactions.port.in;

import java.util.List;

import br.com.pismo.transactions.adapter.in.rest.request.CreateTransactionRequest;
import br.com.pismo.transactions.domain.model.Transaction;

public interface TransactionUC {
    
    void create(CreateTransactionRequest request);

    List<Transaction> getTransactions(Integer accountId);
}
