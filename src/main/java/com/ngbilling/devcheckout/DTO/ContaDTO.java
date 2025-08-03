package com.ngbilling.devcheckout.DTO;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ContaDTO(
        @JsonProperty("numero_conta") @NotNull @Positive Integer numeroConta,
        @NotNull @DecimalMin(value = "0.0") BigDecimal saldo
) {}
