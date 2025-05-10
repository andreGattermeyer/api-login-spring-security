package com.api_login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.api_login.model.Veiculo;
import com.api_login.service.VeiculoService;
import java.util.List;

@RestController
@RequestMapping("/veiculos")
@CrossOrigin(origins = "http://localhost:4200")
public class VeiculoController {
    
    @Autowired
    private VeiculoService veiculoService;

    @PostMapping("/cadastrar-veiculo")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Veiculo> cadastrar(@RequestBody Veiculo veiculo) {
        return ResponseEntity.ok(veiculoService.salvar(veiculo));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<Veiculo>> listarTodos() {
        return ResponseEntity.ok(veiculoService.listarTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")  // Corrigido de 'ROLE_ROLE_ADMIN', 'ROLE_ROLE_USER'
    public ResponseEntity<Veiculo> buscarPorId(@PathVariable Long id) {
        return veiculoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Veiculo> atualizar(@PathVariable Long id, @RequestBody Veiculo veiculo) {
        return ResponseEntity.ok(veiculoService.atualizar(id, veiculo));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        veiculoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}