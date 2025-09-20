package com.ngbilling.devcheckout.Exceptions;

public class ContaNaoEncontradaException extends RuntimeException{
    public ContaNaoEncontradaException() {
        super("Conta não foi encontrada");
    }
    public ContaNaoEncontradaException(String message) {
        super(message);
    }
}
