package br.com.pismo.transactions.domain.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.pismo.transactions.adapter.in.rest.request.CreateTransactionRequest;
import br.com.pismo.transactions.domain.exception.NotFoundException;
import br.com.pismo.transactions.domain.model.Account;
import br.com.pismo.transactions.domain.model.OperationType;
import br.com.pismo.transactions.domain.model.Transaction;
import br.com.pismo.transactions.domain.model.User;
import br.com.pismo.transactions.port.in.AccountUC;
import br.com.pismo.transactions.port.out.TransactionPort;
import br.com.pismo.transactions.port.in.TransactionUC;
import br.com.pismo.transactions.port.out.OperationTypePort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService implements TransactionUC {

    private final AccountUC accountUC;

    private final OperationTypePort operationTypePort;

    private final TransactionPort transactionPort;

    @Override
    public void create(CreateTransactionRequest request) {

        Account account = accountUC.getAccountById(request.getAccountId());

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!account.getUuidUser().equals(user.getId())){
            throw new NotFoundException("Conta não encontrada");
        }
        
        OperationType operationType = operationTypePort.findById(request.getOperationTypeId())
            .orElseThrow(() -> new NotFoundException("Tipo de operação não encontrada"));

        transactionPort.save(Transaction.create(account.getId(), operationType, request.getAmount()));

    }

    @Override
    public List<Transaction> getTransactions(Integer accountId) {

        accountUC.getAccountById(accountId);

        List<OperationType> operationTypes = operationTypePort.findAll();

        List<Transaction> transactions =  transactionPort.findByAccountId(accountId);

        Map<Integer, String> operationTypeMap = operationTypes.stream()
            .collect(Collectors.toMap(OperationType::getId, OperationType::getDescription));

        transactions.forEach(transaction -> {
            String description = operationTypeMap.get(transaction.getOperationTypeId());
            transaction.setOperationDescription(description); 
        });

        return transactions;

    }
    
}
