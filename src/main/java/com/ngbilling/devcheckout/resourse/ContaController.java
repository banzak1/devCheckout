package com.ngbilling.devcheckout.resourse;

import com.ngbilling.devcheckout.DTO.ContaDTO;
import com.ngbilling.devcheckout.service.ContaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/conta")
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping
    public ResponseEntity<ContaDTO> salvarConta(@Valid @RequestBody ContaDTO contaDTO) {
        return contaService.criaConta(contaDTO);
    }

    @GetMapping
    public ResponseEntity<ContaDTO> buscarPorNumeroConta(@RequestParam Integer numeroConta){
        return contaService.buscarConta(numeroConta);
    }
}
