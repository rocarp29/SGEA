package com.templates.apirest.model.dto;

import com.templates.apirest.configuracion.Roles;

import lombok.Data;

@Data
public class UsuarioDTO {
    // Assume getters and setters
    Long id;
    String nombre;
    String password;
    Roles rol;
    String token;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Roles getRol() {
		return rol;
	}
	public void setRol(Roles rol) {
		this.rol = rol;
	}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
  
    public UsuarioDTO(String nombreDTO, String passwordDTO, Roles rolDTO, String tokenDTO){
        this.nombre = nombreDTO;
        this.password = passwordDTO;
        this.rol = rolDTO;
        this.token = tokenDTO;
    }
	public UsuarioDTO() {
	}
    
}
