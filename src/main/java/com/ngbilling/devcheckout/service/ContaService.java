package com.ngbilling.devcheckout.service;

import com.ngbilling.devcheckout.DTO.ContaDTO;
import com.ngbilling.devcheckout.model.Conta;
import com.ngbilling.devcheckout.repository.ContaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class ContaService {
    private final ContaRepository contaRepository;

    public ContaService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    public ResponseEntity<ContaDTO> criaConta(@RequestBody ContaDTO contaDTO) {
        Optional<Conta> contaExistente = contaRepository.findByNumeroConta(contaDTO.numeroConta);
        if (contaExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(contaDTO);
        }

        Conta conta = new Conta();
        conta.setNumeroConta(contaDTO.numeroConta);
        conta.setSaldo(contaDTO.saldo);

        Conta saldo = contaRepository.save(conta);
        ContaDTO responseDTO = new ContaDTO(
                conta.getNumeroConta(),
                conta.getSaldo()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    public ResponseEntity<ContaDTO> buscarConta(Integer numeroConta) {
        return contaRepository.findByNumeroConta(numeroConta)
                .map(conta -> new ContaDTO(conta.getNumeroConta(), conta.getSaldo()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

