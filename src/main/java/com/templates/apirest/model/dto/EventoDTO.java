package com.templates.apirest.model.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.templates.apirest.model.Presentacion;

import lombok.Data;

@Data
public class EventoDTO {
    // Assume getters and setters
    private Long id;
    private String nombre;
    private List<Presentacion> presentaciones;
	private List<String> comentarios;
	private LocalDate fecha;

    public List<Presentacion> getPresentaciones() {
        return presentaciones;
    }

    public void setPresentaciones(List<Presentacion> presentaciones) {
        this.presentaciones = presentaciones;
    }

    public List<String> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<String> comentarios) {
        this.comentarios = comentarios;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
	}
	
	public EventoDTO(){

	}
  
    public EventoDTO(String nombreDTO, ArrayList<Presentacion> presentaciones, List<String> comentariosDTO){
        this.nombre = nombreDTO;
        this.presentaciones = presentaciones;
        this.comentarios = comentarios;
        this.fecha = fecha;
    }

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
    
}
