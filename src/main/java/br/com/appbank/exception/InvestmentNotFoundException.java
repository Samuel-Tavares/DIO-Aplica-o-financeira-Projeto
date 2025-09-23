package br.com.appbank.exception;

public class InvestmentNotFoundException extends RuntimeException {

    public InvestmentNotFoundException(String message) {
        super(message);
    }

}
