package com.ngbilling.devcheckout.resourse;

import com.ngbilling.devcheckout.model.Conta;
import com.ngbilling.devcheckout.repository.ContaRepository;
import org.springframework.web.bind.annotation.*;

@RestController("/conta")
public class ContaController {

    private final ContaRepository repository;

    public ContaController(ContaRepository repository) {
        this.repository = repository;
    }


    @PostMapping
    Conta criarConta(@RequestBody Conta conta){
        return repository.save(conta);
    }

    @GetMapping("/numeroConta/{id}")
    Conta mostrarConta(@PathVariable Long id){
        return repository.findById(id).get();
    }
}
