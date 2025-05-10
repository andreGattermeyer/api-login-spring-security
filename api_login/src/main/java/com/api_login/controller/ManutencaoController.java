package com.api_login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.api_login.model.Manutencao;
import com.api_login.service.ManutencaoService;
import java.util.List;

@RestController
@RequestMapping("/manutencoes")
@CrossOrigin(origins = "http://localhost:4200")
public class ManutencaoController {
    
    @Autowired
    private ManutencaoService manutencaoService;

    @PostMapping("/cadastrar-manutencao")
    @PreAuthorize("hasRole('ADMIN')")  // Simplificado
    public ResponseEntity<Manutencao> cadastrar(@RequestBody Manutencao manutencao) {
        return ResponseEntity.ok(manutencaoService.salvar(manutencao));
    }

    @GetMapping("/veiculo/{veiculoId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")  // Simplificado
    public ResponseEntity<List<Manutencao>> listarPorVeiculo(@PathVariable Long veiculoId) {
        return ResponseEntity.ok(manutencaoService.listarPorVeiculo(veiculoId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")  // Simplificado
    public ResponseEntity<Manutencao> buscarPorId(@PathVariable Long id) {
        return manutencaoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")  // Simplificado
    public ResponseEntity<Manutencao> atualizar(@PathVariable Long id, @RequestBody Manutencao manutencao) {
        return ResponseEntity.ok(manutencaoService.atualizar(id, manutencao));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")  // Simplificado
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        manutencaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}