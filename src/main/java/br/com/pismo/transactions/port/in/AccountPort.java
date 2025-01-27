package br.com.pismo.transactions.port.in;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.pismo.transactions.adapter.in.rest.request.CreateAccountRequest;
import br.com.pismo.transactions.adapter.in.rest.response.AccountResponse;
import br.com.pismo.transactions.adapter.in.rest.response.CreatedAccountResponse;
import br.com.pismo.transactions.adapter.in.rest.response.DefaultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RequestMapping("accounts")
@Tag(name = "Contas", description = "Operações relacionadas às contas de usuários")
public interface AccountPort {

    @PostMapping
    @Operation(
        summary = "Cadastro de conta do usuario",
        description = "Operação para cadastrar contas de usuários"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Cadastro de conta efetuado com sucesso.",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                examples = @ExampleObject(
                    value =  """
                            {
                                "httpStatus": 201,
                                "resultData": {
                                    "document_number": "123456789",
                                    "account_number": "69874",
                                    "account_digit": "7",
                                    "level": "SILVER"
                                }
                            }
                            """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Requisição inválida. Verifique os dados enviados.",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                examples = @ExampleObject(
                    value =  """
                            {
                                "httpStatus": 400,
                                "erros": [
                                    "document_number: document_number é obrigatório; "
                                ]
                            }
                            """
                )
            )
        ),
        @ApiResponse(
            responseCode = "403", 
            description = "Não permitido.",
            content = @Content(schema = @Schema(hidden = true))
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Erro Interno do Servidor.",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                examples = @ExampleObject(
                    value =  """
                            {
                                "httpStatus": 500,
                                "erros": [
                                    "Erro interno do servidor"
                                ]
                            }
                            """
                )
            )
        )
    })
    public ResponseEntity<DefaultResponse<CreatedAccountResponse>> create(@RequestBody @Valid CreateAccountRequest request);

    @GetMapping(path = "/{accountId}")
    @Operation(
        summary = "Obtem os dados da conta de um usuário",
        description = "Operação para obter dados da conta por ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Conta encontrada.",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                examples = @ExampleObject(
                    value =  """
                            {
                                "httpStatus": 200,
                                "resultData": {
                                    "document_number": "123456789",
                                    "account_number": "69874",
                                    "account_digit": "7",
                                    "level": "SILVER"
                                }
                            }
                            """
                )
            )
        ),
        @ApiResponse(
            responseCode = "403", 
            description = "Não permitido.",
            content = @Content(schema = @Schema(hidden = true))
        ),
        
        @ApiResponse(
            responseCode = "404", 
            description = "Conta não encontrada",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                examples = @ExampleObject(
                    value =  """
                            {
                                "httpStatus": 404,
                                "erros": [
                                    "Conta não encontrada"
                                ]
                            }
                            """
                )
            )
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Erro Interno do Servidor.",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                examples = @ExampleObject(
                    value =  """
                            {
                                "httpStatus": 500,
                                "erros": [
                                    "Erro interno do servidor"
                                ]
                            }
                            """
                )
            )
        )
    })
    public ResponseEntity<DefaultResponse<AccountResponse>> getAccountById(@PathVariable(name = "accountId", required = true) final Integer accountId);

    @GetMapping
    @Operation(
        summary = "Obtem os dados das contas do usuário logado",
        description = "Operação para obter dados das contas por usuario"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de Contas encontrada.",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                examples = {@ExampleObject(
                    name = "Exemplo sem contas",
                    value =  """
                            {
                                "httpStatus": 200,
                                "resultData": []
                            }
                            """
                ),
                @ExampleObject(
                    name = "Exemplo com contas",
                    value =  """
                            {
                                "httpStatus": 200,
                                "resultData": [
                                    {
                                        "id": 3,
                                        "uuid_user": "c8455a2c-9729-4149-b62a-69c283c5382b",
                                        "account_number": "8578010",
                                        "account_digit": "8",
                                        "level": "SILVER"
                                    },
                                    {
                                        "id": 4,
                                        "uuid_user": "c8455a2c-9729-4149-b62a-69c283c5382b",
                                        "account_number": "7720139",
                                        "account_digit": "5",
                                        "level": "SILVER"
                                    }
                                ]
                            }
                            """
                )}
            )
        ),
        @ApiResponse(
            responseCode = "403", 
            description = "Não permitido.",
            content = @Content(schema = @Schema(hidden = true))
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Erro Interno do Servidor.",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                examples = @ExampleObject(
                    value =  """
                            {
                                "httpStatus": 500,
                                "erros": [
                                    "Erro interno do servidor"
                                ]
                            }
                            """
                )
            )
        )
    })
    public ResponseEntity<DefaultResponse<List<AccountResponse>>> getOwnAccounts();
    
}
