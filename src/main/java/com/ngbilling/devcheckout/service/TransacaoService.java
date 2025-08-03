package com.ngbilling.devcheckout.service;

import com.ngbilling.devcheckout.DTO.TransacaoDTO;
import com.ngbilling.devcheckout.model.Conta;
import com.ngbilling.devcheckout.repository.ContaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

@Service
public class TransacaoService {

    private final ContaRepository contaRepository;

    public TransacaoService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    public ResponseEntity<Conta> efetuaPagamento(TransacaoDTO transacaoDTO) {
        Optional<Conta> contaExiste  = contaRepository.findByNumeroConta(transacaoDTO.numeroConta());
        if (contaExiste.isPresent()) {
            Conta conta = contaExiste.get();
            BigDecimal taxa = calculaTaxa(transacaoDTO.formaPagamento(), transacaoDTO.valor());
            BigDecimal valorTotal = transacaoDTO.valor().add(taxa);

            if(conta.getSaldo().compareTo(valorTotal) < 0){
                return ResponseEntity.notFound().build();
            }

            conta.setSaldo(conta.getSaldo().subtract(valorTotal));

            Conta contaAtualizada = contaRepository.save(conta);

            return ResponseEntity.status(HttpStatus.CREATED).body(contaAtualizada);
        } else  {
            return ResponseEntity.notFound().build();
        }
    }

    public BigDecimal calculaTaxa(String formaPagamento, BigDecimal valor) {
        switch (formaPagamento){
            case "D":
                return valor.multiply(BigDecimal.valueOf(0.03));
            case "C":
                return valor.multiply(BigDecimal.valueOf(0.05));
            case "P":
                return BigDecimal.ZERO;
            default:
                throw new IllegalArgumentException("FormaPagamento invalido: " + formaPagamento );
        }
    }
}
