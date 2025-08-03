package com.ngbilling.devcheckout.service;

import com.ngbilling.devcheckout.DTO.TransacaoDTO;
import com.ngbilling.devcheckout.model.Conta;
import com.ngbilling.devcheckout.repository.ContaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransacaoServiceTest {

    @Mock
    private ContaRepository contaRepository;

    @InjectMocks
    private TransacaoService transacaoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void efetuarPixComSucesso() {
        Conta conta = new Conta();
        conta.setNumeroConta(123);
        conta.setSaldo(new BigDecimal("100.00"));

        when(contaRepository.findByNumeroConta(123)).thenReturn(Optional.of(conta));
        when(contaRepository.save(any(Conta.class))).thenReturn(conta);

        TransacaoDTO dto = new TransacaoDTO("P", 123, new BigDecimal("50.00"));

        ResponseEntity<Conta> resposta = transacaoService.efetuaPagamento(dto);

        assertEquals(201, resposta.getStatusCodeValue());
        assertNotNull(resposta.getBody());
        assertEquals(new BigDecimal("50.00"), resposta.getBody().getSaldo());
    }

    @Test
    void recusarTransacaoComSaldoInsuficiente() {
        Conta conta = new Conta();
        conta.setNumeroConta(123);
        conta.setSaldo(new BigDecimal("10.00"));

        when(contaRepository.findByNumeroConta(123)).thenReturn(Optional.of(conta));

        TransacaoDTO dto = new TransacaoDTO("C", 123, new BigDecimal("20.00"));

        ResponseEntity<Conta> resposta = transacaoService.efetuaPagamento(dto);

        assertEquals(404, resposta.getStatusCodeValue());
    }

    @Test
    void recusarTransacaoParaContaInexistente() {
        TransacaoDTO dto = new TransacaoDTO("D", 999, new BigDecimal("10.00"));

        when(contaRepository.findByNumeroConta(999)).thenReturn(Optional.empty());

        ResponseEntity<Conta> resposta = transacaoService.efetuaPagamento(dto);

        assertEquals(404, resposta.getStatusCodeValue());
    }

    @Test
    void emitirExcecaoParaFormaPagamentoInvalida() {
        TransacaoDTO dto = new TransacaoDTO("X", 123, new BigDecimal("10.00"));

        assertThrows(IllegalArgumentException.class, () -> {
            transacaoService.calculaTaxa(dto.formaPagamento(), dto.valor());
        });
    }
}

