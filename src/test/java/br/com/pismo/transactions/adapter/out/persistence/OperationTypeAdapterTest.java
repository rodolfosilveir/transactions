package br.com.pismo.transactions.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.pismo.transactions.adapter.out.persistence.entity.OperationTypeEntity;
import br.com.pismo.transactions.adapter.out.persistence.repository.OperationTypeRepository;
import br.com.pismo.transactions.domain.model.OperationType;

@ExtendWith(MockitoExtension.class)
class OperationTypeAdapterTest {

    @Mock
    private OperationTypeRepository operationTypeRepository;

    @InjectMocks
    private OperationTypeAdapter operationTypeAdapter;

    @Test
    @DisplayName("should return OperationType when found by ID")
    void shouldReturnOperationTypeWhenFoundById() {
        Integer operationTypeId = 1;

        OperationTypeEntity operationTypeEntityMock = new OperationTypeEntity(1, "PAGAMENTO");
        when(operationTypeRepository.findById(operationTypeId)).thenReturn(Optional.of(operationTypeEntityMock));

        Optional<OperationType> operationType = operationTypeAdapter.findById(operationTypeId);

        assertTrue(operationType.isPresent());

        verify(operationTypeRepository, times(1)).findById(operationTypeId);
    }

    @Test
    @DisplayName("should return empty Optional when OperationType not found by ID")
    void shouldReturnEmptyOptionalWhenOperationTypeNotFoundById() {
        Integer operationTypeId = 1;

        when(operationTypeRepository.findById(operationTypeId)).thenReturn(Optional.empty());

        Optional<OperationType> operationType = operationTypeAdapter.findById(operationTypeId);

        assertTrue(operationType.isEmpty());
        verify(operationTypeRepository, times(1)).findById(operationTypeId);
    }

    @Test
    @DisplayName("should return all OperationTypes")
    void shouldReturnAllOperationTypes() {
        OperationTypeEntity operationTypeEntityMock = new OperationTypeEntity(1, "PAGAMENTO");
        List<OperationTypeEntity> operationTypeEntities = Collections.singletonList(operationTypeEntityMock);

        when(operationTypeRepository.findAll()).thenReturn(operationTypeEntities);

        List<OperationType> operationTypes = operationTypeAdapter.findAll();

        assertEquals(1, operationTypes.size());

        verify(operationTypeRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("should return empty list when no OperationTypes are found")
    void shouldReturnEmptyListWhenNoOperationTypesAreFound() {
        when(operationTypeRepository.findAll()).thenReturn(Collections.emptyList());

        List<OperationType> operationTypes = operationTypeAdapter.findAll();

        assertTrue(operationTypes.isEmpty());
        verify(operationTypeRepository, times(1)).findAll();
    }
}
