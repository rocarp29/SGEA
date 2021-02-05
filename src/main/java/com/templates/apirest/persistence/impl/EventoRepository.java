package com.templates.apirest.persistence.impl;

import org.springframework.stereotype.Repository;

import com.templates.apirest.model.Evento;
import com.templates.apirest.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    
}

