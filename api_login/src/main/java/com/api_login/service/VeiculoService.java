package com.api_login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.api_login.model.Veiculo;
import com.api_login.repository.VeiculoRepository;
import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    public Veiculo salvar(Veiculo veiculo) {
        // Verificar se já existe veículo com a mesma placa ou chassi
        if (veiculoRepository.findByPlaca(veiculo.getPlaca()).isPresent()) {
            throw new RuntimeException("Já existe um veículo cadastrado com esta placa");
        }
        if (veiculoRepository.findByChassi(veiculo.getChassi()).isPresent()) {
            throw new RuntimeException("Já existe um veículo cadastrado com este chassi");
        }
        return veiculoRepository.save(veiculo);
    }

    public List<Veiculo> listarTodos() {
        return veiculoRepository.findAll();
    }

    public Optional<Veiculo> buscarPorId(Long id) {
        return veiculoRepository.findById(id);
    }

    public void deletar(Long id) {
        veiculoRepository.deleteById(id);
    }

    public Veiculo atualizar(Long id, Veiculo veiculo) {
        if (!veiculoRepository.existsById(id)) {
            throw new RuntimeException("Veículo não encontrado");
        }
        veiculo.setId(id);
        return veiculoRepository.save(veiculo);
    }
}