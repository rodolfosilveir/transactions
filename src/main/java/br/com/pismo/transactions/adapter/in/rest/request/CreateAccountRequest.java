package br.com.pismo.transactions.adapter.in.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
    
}
