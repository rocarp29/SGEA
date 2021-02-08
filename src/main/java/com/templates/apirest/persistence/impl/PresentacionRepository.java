package com.templates.apirest.persistence.impl;

import org.springframework.stereotype.Repository;

import com.templates.apirest.model.Presentacion;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface PresentacionRepository extends JpaRepository<Presentacion, Long> {

    
}

