package br.com.pismo.transactions.port.in;

import br.com.pismo.transactions.adapter.in.rest.request.RegisterRequest;

public interface UserUC {

    void registerUser(RegisterRequest request);
    
}

