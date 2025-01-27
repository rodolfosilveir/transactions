package br.com.pismo.transactions.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.pismo.transactions.adapter.out.persistence.entity.OperationTypeEntity;
import br.com.pismo.transactions.adapter.out.persistence.repository.OperationTypeRepository;
import br.com.pismo.transactions.domain.model.OperationType;
import br.com.pismo.transactions.port.out.OperationTypePort;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OperationTypeAdapter implements OperationTypePort {

    private final OperationTypeRepository operationTypeRepository;
    
    @Override
    public Optional<OperationType> findById(Integer operationTypeId) {
        
        Optional<OperationTypeEntity> operationType = operationTypeRepository.findById(operationTypeId);
        if(operationType.isPresent()) {
            return Optional.of(operationType.get().toDomain());
        }else{
            return Optional.empty();
        }
    }

    @Override
    public List<OperationType> findAll() {
        return operationTypeRepository.findAll().stream().map(OperationTypeEntity::toDomain).toList();
    }
    
}
