package com.ngbilling.devcheckout.service;

import com.ngbilling.devcheckout.DTO.ContaDTO;
import com.ngbilling.devcheckout.DTO.TransacaoDTO;
import com.ngbilling.devcheckout.Exceptions.ContaNaoEncontradaException;
import com.ngbilling.devcheckout.model.Conta;
import com.ngbilling.devcheckout.repository.ContaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

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

        ContaDTO resposta = transacaoService.efetuaPagamento(dto);

        assertNotNull(resposta);
        assertEquals(new BigDecimal("50.00"), resposta.getSaldo());
    }

    @Test
    void efetuarDebitoComSucesso() {
        Conta conta = new Conta();
        conta.setNumeroConta(123);
        conta.setSaldo(new BigDecimal("100.00"));

        when(contaRepository.findByNumeroConta(123)).thenReturn(Optional.of(conta));
        when(contaRepository.save(any(Conta.class))).thenReturn(conta);

        TransacaoDTO dto = new TransacaoDTO("D", 123, new BigDecimal("50.00"));

        ContaDTO resposta = transacaoService.efetuaPagamento(dto);
        ;
        assertNotNull(resposta);
        assertEquals(0, resposta.getSaldo().compareTo(new BigDecimal("48.50")));
    }

    @Test
    void efetuarCreditoComSucesso() {
        Conta conta = new Conta();
        conta.setNumeroConta(123);
        conta.setSaldo(new BigDecimal("100.00"));

        when(contaRepository.findByNumeroConta(123)).thenReturn(Optional.of(conta));
        when(contaRepository.save(any(Conta.class))).thenReturn(conta);

        TransacaoDTO dto = new TransacaoDTO("C", 123, new BigDecimal("50.00"));

        ContaDTO resposta = transacaoService.efetuaPagamento(dto);

        assertNotNull(resposta);
        assertEquals(0, resposta.getSaldo().compareTo(new BigDecimal("47.50")));
    }

    @Test
    void recusarTransacaoComSaldoInsuficiente() {
        Conta conta = new Conta();
        conta.setNumeroConta(123);
        conta.setSaldo(new BigDecimal("10.00"));

        when(contaRepository.findByNumeroConta(123)).thenReturn(Optional.of(conta));

        TransacaoDTO dto = new TransacaoDTO("C", 123, new BigDecimal("20.00"));

        ContaDTO resposta = transacaoService.efetuaPagamento(dto);

        assertEquals(conta.getSaldo(), resposta.getSaldo());
    }

    @Test
    void recusarTransacaoParaContaInexistente() {
        TransacaoDTO dto = new TransacaoDTO("D", 999, new BigDecimal("10.00"));

        when(contaRepository.findByNumeroConta(999)).thenReturn(Optional.empty());

        assertThrows(ContaNaoEncontradaException.class, ()-> transacaoService.efetuaPagamento(dto));
    }

    @Test
    void emitirExcecaoParaFormaPagamentoInvalida() {
        TransacaoDTO dto = new TransacaoDTO("X", 123, new BigDecimal("10.00"));

        assertThrows(IllegalArgumentException.class, () -> transacaoService.calculaTaxa(dto.formaPagamento(), dto.valor()));
    }
}

