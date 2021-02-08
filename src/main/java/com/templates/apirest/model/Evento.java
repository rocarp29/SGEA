package com.templates.apirest.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name= "Evento")
@Table(name = "evento")
public class Evento {
    @Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name= "nombre")
    private String nombre;

    @Column(name= "presentaciones")
    @OneToMany(targetEntity = Presentacion.class, mappedBy="evento", cascade = {CascadeType.MERGE
    }, fetch = FetchType.LAZY)
    private List<Presentacion> presentaciones;

    @Column(name= "comentarios")
    @OneToMany(targetEntity = Comentario.class, mappedBy="evento", cascade = {CascadeType.ALL
    }, fetch = FetchType.LAZY)
    private List<Comentario> comentarios;

    @Column(name= "fecha")
    private LocalDateTime horaFecha;

    public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Evento(String nombre, LocalDateTime fecha){
        this.nombre = nombre;
        this.horaFecha = fecha;
        this.presentaciones = new ArrayList<>();
        this.comentarios = new ArrayList<>();
    }

    public Evento(String nombre, Presentacion presentacion, LocalDateTime fecha){
        this.nombre = nombre;
        this.horaFecha = fecha;
        this.presentaciones = new ArrayList<>();
        this.presentaciones.add(presentacion);
        this.comentarios = new ArrayList<>();
    }

    public Evento(String nombre, Comentario comentario , LocalDateTime fecha){
        this.nombre = nombre;
        this.horaFecha = fecha;
        this.presentaciones = new ArrayList<>();
        this.comentarios = new ArrayList<>();
        this.comentarios.add(comentario);
        
    }
    public Evento(String nombre, Comentario comentario , Presentacion presentacion, LocalDateTime fecha){
        
        this.nombre = nombre;
        this.horaFecha = fecha;
        this.presentaciones = new ArrayList<>();
        this.presentaciones.add(presentacion);
        this.comentarios = new ArrayList<>();
        this.comentarios.add(comentario);
    }
    public List<Presentacion> getPresentaciones() {
        return presentaciones;
    }

    public void setPresentaciones(List<Presentacion> presentaciones) {
        this.presentaciones = presentaciones;
    }

    public void addPresentacion(Presentacion presentacion) {
        this.presentaciones.add(presentacion);
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    
    public void addComentario(Comentario comentario) {
        this.comentarios.add(comentario);
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

    public LocalDateTime getFecha() {
        return horaFecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.horaFecha = fecha;
    }

    public Evento(){
        this.presentaciones = new ArrayList<>();
        this.comentarios = new ArrayList<>();
    }



    
}
