package com.templates.apirest.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.templates.apirest.configuracion.exceptions.PasswordException;
import com.templates.apirest.configuracion.Roles;
import com.templates.apirest.configuracion.exceptions.EventNotFoundException;
import com.templates.apirest.model.Evento;
import com.templates.apirest.model.Usuario;
import com.templates.apirest.model.dto.EventoDTO;
import com.templates.apirest.persistence.impl.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventosService {
    @Autowired
    private EventoRepository eventoRepository;

    @Autowired 
    private UserService userService;


    public Evento crearEvento(Evento evento){
            return eventoRepository.save(evento);

    }

    public Evento actualizarEvento(Evento evento) throws EventNotFoundException{
        Optional<Evento> eventoAModificar = eventoRepository.findById(evento.getId());
        if(eventoAModificar.isPresent()){
            //todo: loger
            System.out.println("Evento presente - modificando....");
            Evento eventoEnModificacion = eventoAModificar.get();
            eventoEnModificacion.setNombre(evento.getNombre());
            eventoEnModificacion.setComentarios(evento.getComentarios());
            eventoEnModificacion.setPresentaciones(evento.getPresentaciones());
            eventoRepository.save(eventoEnModificacion);
            return eventoEnModificacion;
        }else {
            //Log:
            throw new EventNotFoundException("Evento no encontrado: ID: " + evento.getId(), null);
        }
    }

    public List<Evento> obtenerEventos(){
        return eventoRepository.findAll();
    }

    public Evento obtenerEvento(Long id){
        return eventoRepository.findById(id).orElse(null);
    }

    public Evento obtenerEventoPorNombre(String nombre) throws EventNotFoundException{
        
        Optional<Evento> eventoEncontrado = obtenerEventos().stream().filter(
            user -> user.getNombre().equals(nombre)).findFirst();
            if(eventoEncontrado.isPresent()){
                return eventoEncontrado.get();
            }else {
                //Log:
                throw new EventNotFoundException("Evento no encontrado: Nombre: " + nombre, null);
            }
    }

    public boolean validarModificacion(Usuario usuario, String tokenEnviado)
    {
        if(userService.getRolUsuario(usuario).equals(Roles.ADMIN)){
            return userService.validateToken(usuario, tokenEnviado);
        }else{
            return false;
        }
        
   
    }


    public Map<String,String> guardarEvento(Evento evento){
        Map<String, String> response = new HashMap<String,String>();
        try{
            Evento eventoEncontrado = obtenerEventoPorNombre(evento.getNombre());
            response.put("status","error");
            response.put("error", "Evento con nombre "+eventoEncontrado.getNombre() + " ya existe");
            return response;
        
        }catch(EventNotFoundException unfE){
            //Log:
            eventoRepository.save(evento);
            response.put("status","ok");
            response.put("error","");
            return response;
        }

    }


    public Map<String,String> modificarEvento(Evento evento){
        Map<String, String> response = new HashMap<String,String>();
        try{
            Evento eventoEncontrado = obtenerEventoPorNombre(evento.getNombre());
            eventoEncontrado.setComentarios(evento.getComentarios());
            eventoEncontrado.setNombre(evento.getNombre());
            eventoEncontrado.setPresentaciones(evento.getPresentaciones());
            eventoRepository.save(eventoEncontrado);
            response.put("status","ok");
            response.put("mensaje", "Evento modificado");
            response.put("evento", "Evento modificado");

            return response;
        
        }catch(EventNotFoundException unfE){
            //Log:
            
            response.put("status","error");
            response.put("error","No existe evento con nombre asociado");
            //TODO: añadir token
            
            return response;
        }

    }

    public Map<String,String> borrarEvento(Long idEventoABorrar) 
    {
        Map<String, String> response = new HashMap<>();

        try{
            
            Evento eventoABorrar = obtenerEvento(idEventoABorrar);
            eventoRepository.delete(eventoABorrar);
            response.put("status", "ok");    
            response.put("mensaje", "Evento borrado");    
        } catch(EventNotFoundException enfE){
            response.put("status", "error");    
            response.put("error", "Evento no existe o ya fue borrado");    
        }catch(IllegalArgumentException iaE){
            response.put("status", "error");    
            response.put("error", "Evento no existe o ya fue borrado");    
        }
        return response;
    }

	public List<Evento> obtenerTodos() {
		return eventoRepository.findAll();
	}
}
