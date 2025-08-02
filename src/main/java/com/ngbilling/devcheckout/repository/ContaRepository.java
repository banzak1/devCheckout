package com.ngbilling.devcheckout.repository;

import com.ngbilling.devcheckout.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface ContaRepository extends JpaRepository<Conta, Long> {
    Optional<Conta> findByNumeroConta(Integer numeroConta);

}
