package com.ngbilling.devcheckout.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
public class Conta {

    @Id
    private Integer numeroConta;

    private BigDecimal saldo;

}
