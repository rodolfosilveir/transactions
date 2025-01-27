package br.com.pismo.transactions.adapter.in.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
    
    @NotBlank(message = "O login de usuário é obrigatório")
    @Schema(description = "Usuário a ser cadastrado para acesso a aplicação", example = "simpleCustomer")
    String login, 
    
    @NotBlank(message = "password é obrigatório")
    @Schema(description = "Senha do usuário a ser cadastrado para acesso a aplicação", example = "senha123")
    String password, 
    
    @NotBlank(message = "role é obrigatório")
    @Schema(description = "Role de acesso do usuário a ser cadastrado", example = "CUSTOMER")
    String role
) {
    
}
