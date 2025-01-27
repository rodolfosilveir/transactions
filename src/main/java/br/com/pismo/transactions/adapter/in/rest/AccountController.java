package br.com.pismo.transactions.adapter.in.rest;

import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.pismo.transactions.adapter.in.rest.request.CreateAccountRequest;
import br.com.pismo.transactions.adapter.in.rest.response.AccountResponse;
import br.com.pismo.transactions.adapter.in.rest.response.CreatedAccountResponse;
import br.com.pismo.transactions.adapter.in.rest.response.DefaultResponse;
import br.com.pismo.transactions.domain.model.Account;
import br.com.pismo.transactions.port.in.AccountPort;
import br.com.pismo.transactions.port.in.AccountUC;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AccountController implements AccountPort{

    private final AccountUC accountUC;

    @Override
    public ResponseEntity<DefaultResponse<CreatedAccountResponse>> create(CreateAccountRequest request){

        Account account = accountUC.create(request);

        return ResponseEntity.status(HttpStatusCode.valueOf(201))
            .body(DefaultResponse.<CreatedAccountResponse>builder()
                .httpStatus(201)
                .resultData(CreatedAccountResponse.fromDomain(request.getDocumentNumber(), account))
            .build());
    }

    @Override
    public ResponseEntity<DefaultResponse<AccountResponse>> getAccountById(Integer accountId){
        
        Account account = accountUC.getAccountById(accountId);

        return ResponseEntity.ok(DefaultResponse.<AccountResponse>builder()
            .httpStatus(200)
            .resultData(AccountResponse.fromDomain(account))
            .build());
    }

    @Override
    public ResponseEntity<DefaultResponse<List<AccountResponse>>> getOwnAccounts() {
        
        List<Account> accounts = accountUC.getOwnAccounts();

        return ResponseEntity.ok(DefaultResponse.<List<AccountResponse>>builder()
            .httpStatus(200)
            .resultData(AccountResponse.fromDomain(accounts))
            .build());
    }

}
