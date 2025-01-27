package br.com.pismo.transactions.adapter.out.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.pismo.transactions.adapter.out.persistence.entity.AccountEntity;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {

    @Query(value = "SELECT * FROM account WHERE id_user = :userId", nativeQuery = true)
    List<AccountEntity> findByUserId(@Param("userId") UUID userId);
    
}
