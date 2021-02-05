package com.templates.apirest.model.dto;

import com.templates.apirest.model.Evento;
import com.templates.apirest.model.Usuario;

public class GuardarEventoDTO {
    private Evento evento;
    private Usuario usuario;
    private String token;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
}
