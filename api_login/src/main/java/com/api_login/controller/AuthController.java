package com.api_login.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api_login.service.UsuarioService;
import com.api_login.service.JwtService;
import com.api_login.model.Usuario;
import com.api_login.dto.LoginRequest;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Usuario usuario = usuarioService.buscarPorUsername(loginRequest.getUsername())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            if (!usuarioService.verificarSenha(loginRequest.getPassword(), usuario.getPassword())) {
                throw new RuntimeException("Senha incorreta");
            }

            // Usando o novo método específico
            usuario = usuarioService.atualizarStatusOnline(usuario.getUsername(), true);
            
            String token = jwtService.generateToken(usuario);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login realizado com sucesso");
            response.put("status", true);
            response.put("role", usuario.getRole().name());
            response.put("online", usuario.isOnline());
            response.put("token", token);

            return ResponseEntity.ok().body(response);
        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            errorResponse.put("status", false);
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        try {
            String username = jwtService.getUsernameFromToken(token.replace("Bearer ", ""));
            
            usuarioService.atualizarStatusOnline(username, false);
            
            return ResponseEntity.ok().body(Map.of("message", "Logout realizado com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Erro ao realizar logout"));
        }
    }
}
