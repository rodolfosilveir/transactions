package br.com.pismo.transactions.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OperationType {

    private Integer id;

    private String description;
    
}
