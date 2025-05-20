package br.com.pismo.transactions.domain.exception;

public class UnavaliableCreditLimitException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public UnavaliableCreditLimitException(String message) {
        super(message);
    }
    
}
