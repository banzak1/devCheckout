package com.ngbilling.devcheckout.resourse;

import com.ngbilling.devcheckout.DTO.ContaDTO;
import com.ngbilling.devcheckout.DTO.TransacaoDTO;
import com.ngbilling.devcheckout.model.Conta;
import com.ngbilling.devcheckout.service.TransacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping("/transacao")
    public ContaDTO efetuarTransacao(@RequestBody TransacaoDTO transacaoDTO) {
        return transacaoService.efetuaPagamento(transacaoDTO);
    }
}
