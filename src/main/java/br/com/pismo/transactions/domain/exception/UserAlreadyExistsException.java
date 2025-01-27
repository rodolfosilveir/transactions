package br.com.pismo.transactions.domain.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(final String login) {
        super(String.format("Usuário '%s' ja cadastrado", login));
    }
    
}
