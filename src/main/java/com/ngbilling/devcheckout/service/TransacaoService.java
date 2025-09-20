package com.ngbilling.devcheckout.service;

import com.ngbilling.devcheckout.DTO.ContaDTO;
import com.ngbilling.devcheckout.DTO.TransacaoDTO;
import com.ngbilling.devcheckout.Exceptions.ContaNaoEncontradaException;
import com.ngbilling.devcheckout.Exceptions.SaldoInsuficienteException;
import com.ngbilling.devcheckout.model.Conta;
import com.ngbilling.devcheckout.repository.ContaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class TransacaoService {

    private final ContaRepository contaRepository;

    public TransacaoService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    public ContaDTO efetuaPagamento(TransacaoDTO transacaoDTO) {
        Optional<Conta> contaExiste  = contaRepository.findByNumeroConta(transacaoDTO.numeroConta());
        if (contaExiste.isPresent()) {
            Conta conta = contaExiste.get();
            BigDecimal taxa = calculaTaxa(transacaoDTO.formaPagamento(), transacaoDTO.valor());
            BigDecimal valorTotal = transacaoDTO.valor().add(taxa);

            if(conta.getSaldo().compareTo(valorTotal) < 0){
                throw new SaldoInsuficienteException();
            }

            conta.setSaldo(conta.getSaldo().subtract(valorTotal));
            Conta contaAtualizada = contaRepository.save(conta);

            // Converta a entidade Conta para ContaDTO
            return new ContaDTO(contaAtualizada.getId(), contaAtualizada.getNumeroConta(), contaAtualizada.getSaldo());
        } else  {
            throw new ContaNaoEncontradaException();
        }
    }

    public BigDecimal calculaTaxa(String formaPagamento, BigDecimal valor) {
        return switch (formaPagamento) {
            case "D" -> valor.multiply(BigDecimal.valueOf(0.03));
            case "C" -> valor.multiply(BigDecimal.valueOf(0.05));
            case "P" -> BigDecimal.ZERO;
            default -> throw new IllegalArgumentException("FormaPagamento invalido: " + formaPagamento);
        };
    }
}
