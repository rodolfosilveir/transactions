package br.com.pismo.transactions.adapter.out.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pismo.transactions.adapter.out.persistence.entity.OperationTypeEntity;

@Repository
public interface OperationTypeRepository  extends JpaRepository<OperationTypeEntity, Integer> {

    Optional<OperationTypeEntity> findById(Integer operationTypeId);
    
}
