package br.com.pismo.transactions.domain.mock;

import br.com.pismo.transactions.domain.model.OperationType;

public class OperationTypeMock {
    public static OperationType create(Integer id, String description) {
        return OperationType.builder()
            .id(id)
            .description(description)
            .build();
    }
}
