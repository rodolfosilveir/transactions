package br.com.pismo.transactions.adapter.in.rest;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import br.com.pismo.transactions.adapter.in.rest.request.LoginRequest;
import br.com.pismo.transactions.adapter.in.rest.request.RegisterRequest;
import br.com.pismo.transactions.adapter.in.rest.response.DefaultResponse;
import br.com.pismo.transactions.adapter.in.rest.response.LoginResponse;
import br.com.pismo.transactions.domain.model.User;
import br.com.pismo.transactions.domain.service.TokenService;
import br.com.pismo.transactions.port.in.AuthenticationPort;
import br.com.pismo.transactions.port.in.UserUC;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationPort {

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    private final UserUC userUC;

    @Override
    public ResponseEntity<DefaultResponse<LoginResponse>> login(LoginRequest request){
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(request.login(), request.password());
        Authentication auth = authenticationManager.authenticate(usernamePassword);

        String token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(DefaultResponse.<LoginResponse>builder()
            .httpStatus(200)
            .resultData(LoginResponse.builder().token(token).build())
            .build());
    }

    @Override
    public ResponseEntity<Void> register(RegisterRequest request){

        userUC.registerUser(request);

        return ResponseEntity.status(HttpStatusCode.valueOf(201)).build();
    }
    
}
