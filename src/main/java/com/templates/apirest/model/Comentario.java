package com.templates.apirest.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "Comentario")
@Table(name = "comentario")
public class Comentario {
    
    @Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name= "fecha")
    private LocalDate date;

    @Column(name= "contenido")
    private String contenido;

    @ManyToOne()
    @JoinColumn(name = "evento")
    @JsonIgnore
    private Evento evento;

    @ManyToOne(targetEntity = Usuario.class, cascade = {CascadeType.MERGE
    }, fetch = FetchType.EAGER)
    private Usuario usuario;

    public Comentario(){

    }

    public Comentario(LocalDate date,String contenido){
        this.date = date;
        this.contenido = contenido;
    }

    
    public Comentario(LocalDate date,String contenido, Evento evento){
        this.date = date;
        this.contenido = contenido;
        this.evento = evento;
    }

    public Comentario(LocalDate date,String contenido, Evento evento, Usuario usuario){
        this.date = date;
        this.contenido = contenido;
        this.evento = evento;
        this.usuario = usuario;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


    
}
