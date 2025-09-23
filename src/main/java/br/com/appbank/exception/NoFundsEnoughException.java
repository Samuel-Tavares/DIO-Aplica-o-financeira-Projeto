package br.com.appbank.exception;

public class NoFundsEnoughException extends RuntimeException {

    public NoFundsEnoughException(String message) {
        super(message);
    }

}
