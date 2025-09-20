package com.ngbilling.devcheckout.service;

import com.ngbilling.devcheckout.DTO.ContaDTO;
import com.ngbilling.devcheckout.model.Conta;
import com.ngbilling.devcheckout.repository.ContaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContaServiceTest {

    @Mock
    private ContaRepository contaRepository;

    @InjectMocks
    private ContaService contaService;

    @Test
    void criarConta() {
        ContaDTO contaDTO = new ContaDTO(111, new BigDecimal("100.00"));

        when(contaRepository.findByNumeroConta(111)).thenReturn(Optional.empty());
        when(contaRepository.save(Mockito.any(Conta.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ContaDTO response = contaService.criaConta(contaDTO);

        assertNotNull(response);
        assertEquals(contaDTO.getNumeroConta(), response.getNumeroConta());
        assertEquals(contaDTO.getSaldo(), response.getSaldo());
    }

    @Test
    void conflitoSeContaExistente() {
        ContaDTO dto = new ContaDTO(123, new BigDecimal("100.00"));
        Conta contaExistente = new Conta();
        contaExistente.setNumeroConta(123);

        when(contaRepository.findByNumeroConta(123)).thenReturn(Optional.of(contaExistente));

        ContaDTO resposta = contaService.criaConta(dto);

        assertNotNull(resposta);
        assertEquals(dto.getNumeroConta(), resposta.getNumeroConta());
        assertEquals(dto.getSaldo(), resposta.getSaldo());
    }

}
