package com.templates.apirest.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.templates.apirest.configuracion.Roles;

import java.util.List;

import javax.persistence.*;


@Entity
@Table(name = "usuarios")
public class Usuario {

	@Id
	@Column(name = "usuario_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//@JsonProperty("nombre")
	@Column(name = "nombre")
	private String nombre;

	//@JsonProperty("password")
	@JsonIgnore
	@Column(name = "password")
	private String password;

	//@JsonProperty("rol")
	@Column(name = "rol")
	private Roles rol;

	@JsonIgnore
	@Column(name = "token")
	private String token;

	@ManyToMany(mappedBy = "asistentes")
    // @JoinColumn(name = "asistentes")
    @JsonIgnore
	private List<Presentacion> presentaciones;

	@OneToMany(mappedBy = "usuario")
    // @JoinColumn(name = "asistentes")
    @JsonIgnore
	private List<Comentario> comentarios;


	public Usuario(){

	}

	public Usuario(String nombre, String password, Roles rol, String token) {
		this.nombre = nombre;
		this.password = password;
		this.rol = rol;
		this.token = token;
	}

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

	public List<Presentacion> getPresentaciones() {
		return presentaciones;
	}

	public void setAsistentes(List<Presentacion> presentaciones) {
		this.presentaciones = presentaciones;
	}

	public void addAsistencia(Presentacion presentacion){
		this.presentaciones.add(presentacion);
	}

	

	    
}
