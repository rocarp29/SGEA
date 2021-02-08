package com.templates.apirest.configuracion.exceptions;

public class CommentNotFoundException extends RuntimeException{

    /**
     *Serial version ID para UserNotFoundExcept
     */
    private static final long serialVersionUID = 2687519956245470018L;

    public CommentNotFoundException(String errorMessage, Throwable err) {
        super(errorMessage, err);

    
    }
}
