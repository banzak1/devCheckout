package com.ngbilling.devcheckout.DTO;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ContaDTO{
    private Long id;
    @JsonProperty("numero_conta")
    private Integer numeroConta;
    private BigDecimal saldo;


    public ContaDTO() {}

    public ContaDTO(Long id, Integer numeroConta, BigDecimal saldo) {
        this.id = id;
        this.numeroConta = numeroConta;
        this.saldo = saldo;
    }

    public ContaDTO(Integer numeroConta, BigDecimal saldo) {
        this.numeroConta = numeroConta;
        this.saldo = saldo;
    }
}

