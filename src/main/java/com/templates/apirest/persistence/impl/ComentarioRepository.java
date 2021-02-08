package com.templates.apirest.persistence.impl;

import org.springframework.stereotype.Repository;

import com.templates.apirest.model.Comentario;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    
}

