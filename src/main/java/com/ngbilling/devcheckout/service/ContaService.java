package com.ngbilling.devcheckout.service;

import com.ngbilling.devcheckout.DTO.ContaDTO;
import com.ngbilling.devcheckout.Exceptions.ContaJaExistenteException;
import com.ngbilling.devcheckout.model.Conta;
import com.ngbilling.devcheckout.repository.ContaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContaService {
    private final ContaRepository contaRepository;

    public ContaService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    public ContaDTO criaConta(ContaDTO contaDTO) {
        Optional<Conta> contaExistente = contaRepository.findByNumeroConta(contaDTO.getNumeroConta());
        if (contaExistente.isPresent()) {
            throw new ContaJaExistenteException("Conta com número " + contaDTO.getNumeroConta() + " já existe");

        }
        Conta conta = new Conta();
        conta.setNumeroConta(contaDTO.getNumeroConta());
        conta.setSaldo(contaDTO.getSaldo());

        Conta contaCriada = contaRepository.save(conta);

        return new ContaDTO(contaCriada.getNumeroConta(), contaCriada.getSaldo());
    }

    public ContaDTO buscarConta(Integer numeroConta) {
        return contaRepository.findByNumeroConta(numeroConta)
                .map(conta -> new ContaDTO(conta.getNumeroConta(), conta.getSaldo()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build()).getBody();
    }
}

