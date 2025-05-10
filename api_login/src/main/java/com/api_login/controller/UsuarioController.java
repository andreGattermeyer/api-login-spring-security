package com.api_login.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.api_login.model.Usuario;
import com.api_login.service.UsuarioService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping(value = "/cadastro", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario) {
        try {
            usuario.setPassword(usuarioService.criptografarSenha(usuario.getPassword()));
            Usuario usuarioSalvo = usuarioService.salvarUsuario(usuario);
            return ResponseEntity.ok(usuarioSalvo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")  // Apenas admin pode listar todos os usuários
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.listarTodos();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")  // Admin e usuário podem ver detalhes
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        try {
            Optional<Usuario> usuario = usuarioService.buscarPorId(id);
            return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")  // Apenas admin pode deletar
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        if (!usuarioService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")  // Admin e usuário podem atualizar
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioAtualizado) {
        try {
            // Verifica se o usuário existe
            Usuario usuarioExistente = usuarioService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Usuário com ID " + id + " não encontrado."));

            // Atualiza os campos que não estão vazios
            if (usuarioAtualizado.getUsername() != null && !usuarioAtualizado.getUsername().trim().isEmpty()) {
                usuarioExistente.setUsername(usuarioAtualizado.getUsername().trim());
            }

            if (usuarioAtualizado.getPassword() != null && !usuarioAtualizado.getPassword().trim().isEmpty()) {
                usuarioExistente.setPassword(usuarioService.criptografarSenha(usuarioAtualizado.getPassword().trim()));
            }

            // Salva as alterações
            Usuario usuarioSalvo = usuarioService.salvarUsuario(usuarioExistente);

            return ResponseEntity.ok(usuarioSalvo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null); // Retorna um erro 404 se o usuário não for encontrado
        } catch (Exception e) {
            return ResponseEntity.status(500).build(); // Retorna erro interno caso ocorra algum problema
        }
    }
}