package com.ngbilling.devcheckout.Exceptions;

public class ContaJaExistenteException extends RuntimeException{
    public ContaJaExistenteException() {
        super("Conta não foi encontrada");
    }
    public ContaJaExistenteException(String message) {
        super(message);
    }
}
