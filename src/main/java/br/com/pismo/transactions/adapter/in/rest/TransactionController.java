package br.com.pismo.transactions.adapter.in.rest;

import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.pismo.transactions.adapter.in.rest.request.CreateTransactionRequest;
import br.com.pismo.transactions.adapter.in.rest.response.AccountBalanceResponse;
import br.com.pismo.transactions.adapter.in.rest.response.DefaultResponse;
import br.com.pismo.transactions.domain.model.Account;
import br.com.pismo.transactions.domain.model.Transaction;
import br.com.pismo.transactions.port.in.AccountUC;
import br.com.pismo.transactions.port.in.TransactionPort;
import br.com.pismo.transactions.port.in.TransactionUC;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TransactionController implements TransactionPort {

    private final TransactionUC transactionUC;

    private final AccountUC accountUC;

    @Override
    public ResponseEntity<Void> create(CreateTransactionRequest request) {

        transactionUC.create(request);

        return ResponseEntity.status(HttpStatusCode.valueOf(201)).build();
    }

    @Override
    public ResponseEntity<DefaultResponse<AccountBalanceResponse>> getBalanceAccount(Integer accountId) {

        Account account = accountUC.getAccountById(accountId);

        List<Transaction> transactions = transactionUC.getTransactions(accountId);
        
        return ResponseEntity.ok(DefaultResponse.<AccountBalanceResponse>builder()
            .httpStatus(200)
            .resultData(AccountBalanceResponse.fromDomain(transactions, account.getAvaliableCreditLimit()))
            .build());
    }

    
    
}
