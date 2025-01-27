package br.com.pismo.transactions.port.in;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.pismo.transactions.adapter.in.rest.request.LoginRequest;
import br.com.pismo.transactions.adapter.in.rest.request.RegisterRequest;
import br.com.pismo.transactions.adapter.in.rest.response.DefaultResponse;
import br.com.pismo.transactions.adapter.in.rest.response.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RequestMapping("auth")
@Tag(name = "Autenticação", description = "Operações relacionadas a logins e usuários")
public interface AuthenticationPort {

    @PostMapping("/login")
    @Operation(
        summary = "Login",
        description = "Operação para se autenticar na aplicação"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Login efetuado com sucesso.",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                examples = @ExampleObject(
                    value =  """
                            {
                                "httpStatus": 200,
                                "resultData": {
                                    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoLWFwaSIsInN1YiI6ImFkbWluIiwiZXhwIjoxNzM3OTEyNDI2fQ.NFELWf1IVz4TBRN0_be-IIjL4o2kTbskeKXt73Jnnyg"
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
                                    "login: O login de usuário é obrigatório; "
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
    public ResponseEntity<DefaultResponse<LoginResponse>> login(@RequestBody @Valid LoginRequest request);

    @PostMapping("/register")
    @Operation(
        summary = "Cadastro de usuario",
        description = "Operação para cadastrar novos usuários"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Cadastro de usuario efetuado com sucesso.",
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
                                    "login: O login de usuário é obrigatório; ",
                                    "password: password é obrigatório; "
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
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequest request);
    
}
