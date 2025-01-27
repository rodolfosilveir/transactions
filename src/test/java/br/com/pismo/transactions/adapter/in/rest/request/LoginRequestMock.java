package br.com.pismo.transactions.adapter.in.rest.request;

public class LoginRequestMock {

    public static LoginRequest create(String login, String password){
        return new LoginRequest(login, password);
    }
    
}

