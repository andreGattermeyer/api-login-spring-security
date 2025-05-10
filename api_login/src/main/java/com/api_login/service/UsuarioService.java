package com.api_login.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.api_login.model.Usuario;
import com.api_login.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public String criptografarSenha(String senha) {
        return passwordEncoder.encode(senha);
    }

    public boolean verificarSenha(String senhaDigitada, String senhaArmazenada) {
        return passwordEncoder.matches(senhaDigitada, senhaArmazenada);
    }
    
    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public Usuario salvarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }
    
    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario atualizarStatusOnline(String username, boolean status) {
        Usuario usuario = buscarPorUsername(username)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        usuario.setOnline(status);
        return usuarioRepository.save(usuario);
    }

    @Scheduled(fixedRate = 300000) // Executa cada 5 minutos
    public void verificarUsuariosInativos() {
        List<Usuario> usuariosOnline = usuarioRepository.findByOnlineTrue();
        for (Usuario usuario : usuariosOnline) {
            usuario.setOnline(false);
            usuarioRepository.save(usuario);
        }
    }

    // Método para buscar usuarios online
    public List<Usuario> buscarUsuariosOnline() {
        return usuarioRepository.findByOnlineTrue();
    }
}
