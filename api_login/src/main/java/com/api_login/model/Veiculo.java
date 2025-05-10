package com.api_login.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Modelo é obrigatório")
    @Column(nullable = false)
    private String modelo;

    @NotBlank(message = "Fabricante é obrigatório")
    @Column(nullable = false)
    private String fabricante;

    @NotBlank(message = "Cor é obrigatória")
    @Column(nullable = false)
    private String cor;

    @NotBlank(message = "Chassi é obrigatório")
    @Pattern(regexp = "^[A-Z0-9]{17}$", message = "Chassi deve ter 17 caracteres alfanuméricos")
    @Column(nullable = false, unique = true)
    private String chassi;

    @NotBlank(message = "Placa é obrigatória")
    @Pattern(regexp = "^[A-Z]{3}[0-9][A-Z][0-9]{2}$", message = "Placa deve estar no formato Mercosul")
    @Column(nullable = false, unique = true)
    private String placa;

    @NotBlank(message = "Frota é obrigatória")
    @Column(nullable = false)
    private String frota;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVeiculo tipoVeiculo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusVeiculo status;

    @CreationTimestamp
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;

    // Enums
    public enum TipoVeiculo {
        CARRO, MOTO, CAMINHAO, VAN, ONIBUS
    }

    public enum StatusVeiculo {
        ATIVO, INATIVO, EM_MANUTENCAO, VENDIDO
    }

    // Construtor
    public Veiculo() {
        this.status = StatusVeiculo.ATIVO; // Status padrão ao criar
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getChassi() {
        return chassi;
    }

    public void setChassi(String chassi) {
        this.chassi = chassi;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getFrota() {
        return frota;
    }

    public void setFrota(String frota) {
        this.frota = frota;
    }

    @Override
    public String toString() {
        return "Veiculo{" +
                "id=" + id +
                ", modelo='" + modelo + '\'' +
                ", fabricante='" + fabricante + '\'' +
                ", placa='" + placa + '\'' +
                ", status=" + status +
                '}';
    }

    // Getters e Setters para os novos campos
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setTipoVeiculo(TipoVeiculo tipoVeiculo) {
        this.tipoVeiculo = tipoVeiculo;
    }

    public TipoVeiculo getTipoVeiculo() {
        return tipoVeiculo;
    }

    public void setStatus(StatusVeiculo status) {
        this.status = status;
    }

    public StatusVeiculo getStatus() {
        return status;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario; // Usuário responsável pelo veículo

    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Manutencao> manutencoes; // Lista de manutenções do veículo

    // Getters e Setters adicionais
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Manutencao> getManutencoes() {
        return manutencoes;
    }

    public void setManutencoes(List<Manutencao> manutencoes) {
        this.manutencoes = manutencoes;
    }
}