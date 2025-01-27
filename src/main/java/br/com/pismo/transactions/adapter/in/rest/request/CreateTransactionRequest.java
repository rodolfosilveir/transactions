package br.com.pismo.transactions.adapter.in.rest.request;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;

@Generated
@Getter
@AllArgsConstructor
public class CreateTransactionRequest{

    @NotNull(message = "account_id é obrigatório")
    @Schema(description = "ID da conta", example = "1")
    @JsonProperty("account_id")
    private Integer accountId;

    @NotNull(message = "operation_type_id é obrigatório")
    @Schema(description = "ID do tipo de operação da transação", example = "PAGAMENTO")
    @JsonProperty("operation_type_id")
    private Integer operationTypeId;

    @NotNull(message = "amount é obrigatório")
    @Schema(description = "Valor da transação", example = "238.76")
    private BigDecimal amount;
    
}
    
