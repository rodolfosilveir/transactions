package br.com.pismo.transactions.adapter.out.persistence;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.pismo.transactions.adapter.out.persistence.entity.TransactionEntity;
import br.com.pismo.transactions.adapter.out.persistence.repository.TransactionRepository;
import br.com.pismo.transactions.domain.model.Transaction;
import br.com.pismo.transactions.port.out.TransactionPort;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TransactionAdapter implements TransactionPort {

    private final TransactionRepository transactionRepository;

    @Override
    public void save(Transaction transaction) {
        transactionRepository.save(TransactionEntity.fromDomain(transaction));
    }

    @Override
    public List<Transaction> findByAccountId(Integer accountId) {
        return transactionRepository.findByAccountId(accountId).stream()
            .map(TransactionEntity::toDomain)
            .collect(Collectors.toList());
    }
    
}
