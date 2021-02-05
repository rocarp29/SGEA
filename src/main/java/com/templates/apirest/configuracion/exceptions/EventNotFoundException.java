package com.templates.apirest.configuracion.exceptions;

public class EventNotFoundException extends RuntimeException{

    /**
     *Serial version ID para UserNotFoundExcept
     */
    private static final long serialVersionUID = 2687519956245470018L;

    public EventNotFoundException(String errorMessage, Throwable err) {
        super(errorMessage, err);

    
    }
}
