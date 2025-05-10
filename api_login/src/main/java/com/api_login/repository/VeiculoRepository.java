package com.api_login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.api_login.model.Veiculo;
import java.util.Optional;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    Optional<Veiculo> findByPlaca(String placa);
    Optional<Veiculo> findByChassi(String chassi);
}