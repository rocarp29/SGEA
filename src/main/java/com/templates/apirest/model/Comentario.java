package com.templates.apirest.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "comentario")
public class Comentario {
    
    @Id
	@Column(name = "comentario_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name= "fecha")
    private LocalDate date;

    @Column(name= "contenido")
    private String contenido;

    @ManyToOne()
    @JoinColumn(name = "evento_id")
    private Evento evento;

    public Comentario(){

    }

    public Comentario(LocalDate date,String contenido){
        this.date = date;
        this.contenido = contenido;
    }


    
}
