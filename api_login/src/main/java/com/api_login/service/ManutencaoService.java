package com.api_login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.api_login.model.Manutencao;
import com.api_login.repository.ManutencaoRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ManutencaoService {

    @Autowired
    private ManutencaoRepository manutencaoRepository;

    public Manutencao salvar(Manutencao manutencao) {
        return manutencaoRepository.save(manutencao);
    }

    public List<Manutencao> listarPorVeiculo(Long veiculoId) {
        return manutencaoRepository.findByVeiculoId(veiculoId);
    }

    public Optional<Manutencao> buscarPorId(Long id) {
        return manutencaoRepository.findById(id);
    }

    public Manutencao atualizar(Long id, Manutencao manutencao) {
        if (!manutencaoRepository.existsById(id)) {
            throw new RuntimeException("Manutenção não encontrada");
        }
        manutencao.setId(id);
        return manutencaoRepository.save(manutencao);
    }

    public void deletar(Long id) {
        manutencaoRepository.deleteById(id);
    }
}