package com.templates.apirest.configuracion.exceptions;

public class PasswordException extends RuntimeException{

    /**
     *Serial version ID para UserNotFoundExcept
     */
    private static final long serialVersionUID = 2687519956245470018L;

    public PasswordException(String errorMessage, Throwable err) {
        super(errorMessage, err);

    
    }
}
