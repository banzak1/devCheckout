package com.ngbilling.devcheckout.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "conta")
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numeroConta;

    private BigDecimal saldo;

}
