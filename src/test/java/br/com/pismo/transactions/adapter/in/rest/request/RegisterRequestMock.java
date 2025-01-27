package br.com.pismo.transactions.adapter.in.rest.request;

public class RegisterRequestMock {

    public static RegisterRequest create(String login, String role){
        return new RegisterRequest(
            login,
            "password",
            role
        );
    }
    
}
