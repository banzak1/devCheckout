package com.ngbilling.devcheckout.DTO;

import java.math.BigDecimal;

public record TransacaoDTO (
    String formaPagamento,
    Integer numeroConta,
    BigDecimal valor
){}
