package br.com.pismo.transactions.port.in;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.pismo.transactions.adapter.in.rest.request.CreateTransactionRequest;
import br.com.pismo.transactions.adapter.in.rest.response.AccountBalanceResponse;
import br.com.pismo.transactions.adapter.in.rest.response.DefaultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RequestMapping("transactions")
@Tag(name = "Transações", description = "Operações relacionadas às transações das contas de usuários")
public interface TransactionPort {

    @PostMapping
    @Operation(
        summary = "Criação de uma transação",
        description = "Operação para criar transações de contas"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Transação criada com sucesso com sucesso.",
            content = @Content(schema = @Schema(hidden = true))
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
                                    "account_id: account_id é obrigatório; "
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
    public ResponseEntity<Void> create(@RequestBody @Valid CreateTransactionRequest request);

    @GetMapping("/{accountId}/balance")
    @Operation(
        summary = "Obter Transações e balanço da conta",
        description = "Operação para ler todas as transações da conta e sumarizar o saldo da conta"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Transações retornadas com sucesso.",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                examples = @ExampleObject(
                    value =  """
                            {
                                "httpStatus": 200,
                                "resultData": {
                                    "balance": 6.90,
                                    "transactions": [
                                        {
                                            "id": "1",
                                            "operationDescription": "COMPRA PARCELADA",
                                            "amount": -243.20,
                                            "eventDate": "2025-01-26T19:33:31.213332"
                                        },
                                        {
                                            "id": "2",
                                            "operationDescription": "PAGAMENTO",
                                            "amount": 100.20,
                                            "eventDate": "2025-01-26T19:34:13.602426"
                                        },
                                        {
                                            "id": "3",
                                            "operationDescription": "PAGAMENTO",
                                            "amount": 100.20,
                                            "eventDate": "2025-01-26T19:37:20.554422"
                                        },
                                        {
                                            "id": "4",
                                            "operationDescription": "PAGAMENTO",
                                            "amount": 100.20,
                                            "eventDate": "2025-01-26T19:38:32.570854"
                                        },
                                        {
                                            "id": "5",
                                            "operationDescription": "COMPRA A VISTA",
                                            "amount": -50.50,
                                            "eventDate": "2025-01-26T19:39:03.304305"
                                        }
                                    ]
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
    public ResponseEntity<DefaultResponse<AccountBalanceResponse>> getBalanceAccount(@PathVariable(name = "accountId", required = true) final Integer accountId);
    
}
