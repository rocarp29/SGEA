package com.templates.apirest.persistence.impl;

import com.templates.apirest.model.Evento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    
}

