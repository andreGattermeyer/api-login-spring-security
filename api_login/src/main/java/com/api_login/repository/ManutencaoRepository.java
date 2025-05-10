package com.api_login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.api_login.model.Manutencao;
import java.util.List;

public interface ManutencaoRepository extends JpaRepository<Manutencao, Long> {
    List<Manutencao> findByVeiculoId(Long veiculoId);
}