package com.templates.apirest.configuracion;

public enum Roles {
    ADMIN("ADMIN",5), USUARIO("USUARIO", 1), VISITANTE("VISITANTE",0);

    private int prioridad;
    private String nombre;

    private Roles(String v, int p){
        prioridad = p;
        nombre = v;
    }
    
    public int getPrioridad(){
        return prioridad;
    }
    public String getValue(){
        return nombre;
    }
}

