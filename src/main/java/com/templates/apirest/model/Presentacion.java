package com.templates.apirest.model;

import java.time.LocalDateTime;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity(name = "Presentacion")
@Table(name = "presentacion")
public class Presentacion {
    
    @Id
	@Column(name = "presentacion_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "fecha")
    private LocalDateTime date;

    @Column(name= "titulo")
    private String titulo;

    @Column(name= "ponente")
    private String ponente;

    @Column(name= "asistentes")
    //fetchtype lazy porque puede haber demasiados asistentes. 
    @ManyToMany(targetEntity = Usuario.class, cascade = {CascadeType.MERGE
    }, fetch = FetchType.LAZY)
    // @JoinColumn()
    private List<Usuario> asistentes;

    @ManyToOne()
    @JoinColumn(name = "evento")
    @JsonIgnore
    private Evento evento;

    

    public Presentacion(String titulo, String ponente){
        this.date = LocalDateTime.now();
        this.titulo = titulo;
        this.ponente  = ponente;
        this.asistentes = new ArrayList<>();
    }

    public Presentacion(LocalDateTime date, String titulo, String ponente){
        this.date = date;
        this.titulo = titulo;
        this.ponente = ponente;
        this.asistentes = new ArrayList<>();
    }

    public Presentacion(LocalDateTime date, String titulo, String ponente, Evento evento){
        this.date = date;
        this.titulo = titulo;
        this.ponente = ponente;
        this.asistentes = new ArrayList<>();
        this.evento = evento;
    }



    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPonente() {
        return ponente;
    }

    public void setPonente(String ponente) {
        this.ponente = ponente;
    }

    public List<Usuario> getAsistentes() {
        return asistentes;
    }

    public void setAsistentes(List<Usuario> asistentes) {
        this.asistentes = asistentes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public void addAsistente(Usuario usuario){
        this.asistentes.add(usuario);
        
    }
    public Presentacion(){

    }


    
}
