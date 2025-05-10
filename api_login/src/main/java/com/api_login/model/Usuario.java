package com.api_login.model;


import java.util.List;

import com.api_login.service.UsuarioService;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String username;
	
	private String email;
	
	private String password;

	@Column(nullable = false)
    private boolean online = false; // valor padrão é false
	
	
	 public void setPassword(String password, UsuarioService usuarioService) {
	        this.password = usuarioService.criptografarSenha(password);
	    }
	
	
	@Enumerated(EnumType.STRING) // Armazena como texto no banco
    private Role role;
	
	  public Usuario() {
	    }

		public Usuario(String username, String email, String password, Role role) {
			super();
			this.username = username;
			this.email = email;
			this.password = password;
			this.role = role;	
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public Role getRole() {
			return role;
		}

		public void setRole(Role role) {
			this.role = role;
		}

		public boolean isOnline() {
			return online;
		}
		
		public void setOnline(boolean online) {
			this.online = online;
		}
	
	

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Veiculo> veiculos;

    public List<Veiculo> getVeiculos() {
        return veiculos;
    }

    public void setVeiculos(List<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }
}
