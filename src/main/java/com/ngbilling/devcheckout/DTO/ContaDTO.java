package com.ngbilling.devcheckout.DTO;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record ContaDTO(
        @JsonProperty("numero_conta")
        Integer numeroConta,
        BigDecimal saldo
) {}
