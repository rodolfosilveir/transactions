package br.com.pismo.transactions.domain.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.pismo.transactions.adapter.in.rest.request.CreateAccountRequest;
import br.com.pismo.transactions.domain.exception.NotFoundException;
import br.com.pismo.transactions.domain.model.Account;
import br.com.pismo.transactions.domain.model.User;
import br.com.pismo.transactions.port.in.AccountUC;
import br.com.pismo.transactions.port.out.AccountPort;
import br.com.pismo.transactions.port.out.UserPort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService implements AccountUC {

    private final UserPort userPort;

    private final AccountPort accountPort;

    @Override
    public Account create(CreateAccountRequest request) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(StringUtils.isBlank(user.getDocumentNumber())){
            user.setDocumentNumber(request.getDocumentNumber());
            userPort.save(user);
        }

        try{
            return accountPort.save(user, new Account());
        }catch(DataIntegrityViolationException e){
            return accountPort.save(user, new Account());
        }        

    }

    @Override
    public Account getAccountById(Integer accountId) {
        return accountPort.findById(accountId)
            .orElseThrow(() -> new NotFoundException("Conta não encontrada"));
    }

    @Override
    public List<Account> getOwnAccounts() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return accountPort.findByUser(user);
    }

    
    
}
