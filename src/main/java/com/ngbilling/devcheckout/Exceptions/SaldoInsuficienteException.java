package com.ngbilling.devcheckout.Exceptions;

import com.ngbilling.devcheckout.DTO.TransacaoDTO;

public class SaldoInsuficienteException extends RuntimeException{
    public SaldoInsuficienteException() {
        super("Saldo Insuficiente");
    }

    public SaldoInsuficienteException(String message) {
        super (message);
    }
}
