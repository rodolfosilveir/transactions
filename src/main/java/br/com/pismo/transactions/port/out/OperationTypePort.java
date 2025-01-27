package br.com.pismo.transactions.port.out;

import java.util.List;
import java.util.Optional;

import br.com.pismo.transactions.domain.model.OperationType;

public interface OperationTypePort {
    
    Optional<OperationType> findById(Integer operationTypeId);

    List<OperationType> findAll();
}
