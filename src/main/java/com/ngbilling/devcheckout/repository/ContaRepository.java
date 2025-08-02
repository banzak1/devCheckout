package com.ngbilling.devcheckout.repository;

import com.ngbilling.devcheckout.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, Long> {

}
