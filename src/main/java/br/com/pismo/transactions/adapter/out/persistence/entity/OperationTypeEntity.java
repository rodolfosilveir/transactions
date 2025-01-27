package br.com.pismo.transactions.adapter.out.persistence.entity;

import br.com.pismo.transactions.domain.model.OperationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "operation_type")
@NoArgsConstructor
@AllArgsConstructor
public class OperationTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "description")
    private String description;

    public OperationType toDomain(){
        return OperationType.builder()
            .id(id)
            .description(description)
            .build();
    }
    
}
