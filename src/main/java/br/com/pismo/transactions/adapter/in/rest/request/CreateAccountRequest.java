package br.com.pismo.transactions.adapter.in.rest.request;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;

@Generated
@Getter
@AllArgsConstructor
public class CreateAccountRequest{

    @NotBlank(message = "document_number é obrigatório")
    @Schema(description = "Numero de document do usuario para cadastro de conta", example = "123456789")
    @JsonProperty("document_number")
    private String documentNumber;

    @NotNull(message = "avaliableCreditLimit é obrigatório")
    @Schema(description = "Valor de limite de crédito", example = "5000.00")
    private BigDecimal avaliableCreditLimit;
    
}
