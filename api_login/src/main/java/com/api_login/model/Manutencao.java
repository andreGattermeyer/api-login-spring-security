package com.api_login.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
public class Manutencao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    @Column(nullable = false)
    private LocalDateTime dataManutencao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoManutencao tipoManutencao;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private BigDecimal custo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusManutencao status;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public LocalDateTime getDataManutencao() {
        return dataManutencao;
    }

    public void setDataManutencao(LocalDateTime dataManutencao) {
        this.dataManutencao = dataManutencao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    
}
