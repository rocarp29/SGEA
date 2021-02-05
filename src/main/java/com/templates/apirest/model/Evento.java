package com.templates.apirest.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "evento")
public class Evento {
    @Id
	@Column(name = "evento_Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name= "nombre")
    private String nombre;

    @Column(name= "presentacion")
    @JoinColumn(name = "presentacion_id", nullable = false, referencedColumnName="presentacion_id")
    // @OneToMany(mappedBy = "evento", cascade = {CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true)
    private Presentacion presentaciones;

    @Column(name= "comentario")
    @JoinColumn(name = "comentario_id", nullable = false,referencedColumnName="comentario_id")
    // @OneToMany(mappedBy = "evento", cascade = {CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true)
    private Comentario comentarios;

    @Column(name= "fecha")
    private LocalDateTime horaFecha;

    public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Evento(String nombre, LocalDateTime fecha){
        this.nombre = nombre;
        this.horaFecha = fecha;
        // this.presentaciones = new ArrayList<>();
        // this.comentarios = new ArrayList<>();
    }

    public Evento(String nombre, Presentacion presentacion, LocalDateTime fecha){
        this.nombre = nombre;
        this.horaFecha = fecha;
        // this.presentaciones = new ArrayList<>();
        this.presentaciones = presentacion;
        // this.comentarios = new ArrayList<>();
    }

    public Evento(String nombre, Comentario comentario , LocalDateTime fecha){
        this.nombre = nombre;
        this.horaFecha = fecha;
        // this.presentaciones = new ArrayList<>();
        // this.presentaciones = presentacion;
        // this.comentarios = new ArrayList<>();
        this.comentarios = (comentario);
        
    }
    public Evento(String nombre, Comentario comentario , Presentacion presentacion, LocalDateTime fecha){
        
        this.nombre = nombre;
        this.horaFecha = fecha;
        // this.presentaciones = new ArrayList<>();
        this.presentaciones = presentacion;
        // this.comentarios = new ArrayList<>();
        this.comentarios= (comentario);
    }
    public Presentacion getPresentaciones() {
        return presentaciones;
    }

    public void setPresentaciones(Presentacion presentaciones) {
        this.presentaciones = presentaciones;
    }

    public void addPresentacion(Presentacion presentacion) {
        this.presentaciones = presentacion;
    }

    public Comentario getComentarios() {
        return comentarios;
    }

    public void setComentarios(Comentario comentarios) {
        this.comentarios = comentarios;
    }

    
    // public void addComentario(Comentario comentario) {
    //     this.comentarios.add(comentario);
    // }

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
        
        // this.presentaciones = new ArrayList<>();
        // this.comentarios = new ArrayList<>();
    }



    
}
