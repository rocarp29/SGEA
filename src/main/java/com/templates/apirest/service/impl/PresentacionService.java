package com.templates.apirest.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import com.templates.apirest.configuracion.exceptions.CommentNotFoundException;
import com.templates.apirest.configuracion.exceptions.EventNotFoundException;
import com.templates.apirest.configuracion.exceptions.PresentationNotFoundException;
import com.templates.apirest.model.Evento;
import com.templates.apirest.model.Presentacion;
import com.templates.apirest.model.Usuario;
import com.templates.apirest.persistence.impl.PresentacionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PresentacionService {
    
    private PresentacionRepository presentacionRepository;

     
    private UserService userService;
    
    
    @Autowired 
    private EventoService eventoService;

    public Presentacion crearPresentacion(Presentacion presentacion){
        
        if(filtrarPresentacion(presentacion.getTitulo(), presentacion.getPonente()).isEmpty()){
            return null;
        }else{
            return presentacionRepository.save(presentacion);
        }
        
    }


    public List<Presentacion> filtrarPresentacion(String titulo, String ponente){
        return presentacionRepository.findAll().stream().filter(
            p -> p.getTitulo().equals(titulo) &&
             p.getPonente().equals(ponente)).collect(Collectors.toList());

    }

    public Presentacion modificarPresentacion(Presentacion presentacion){

        return presentacionRepository.save(presentacion);
        

    }

    public Presentacion obtenerPresentacionPorId(Long id){
        return presentacionRepository.findById(id).orElse(null);
    }



    public void addAsistente(Presentacion presentacion, Usuario usuario){
        presentacion.addAsistente(usuario);

        modificarPresentacion(presentacion);

    }


    public List<Presentacion> obtenerPresentaciones(){
        return presentacionRepository.findAll();
    }

    public List<Presentacion> obtenerPresentacionsPorEvento(Evento evento) throws EventNotFoundException{
        try{
            List<Presentacion> presentacionsEncontrados = obtenerPresentaciones().stream().filter(
            presentacion -> evento.getId().equals(presentacion.getId())).collect(Collectors.toList());
            if(!presentacionsEncontrados.isEmpty() ){
                return presentacionsEncontrados;
            }else {
                //Log:
                throw new PresentationNotFoundException("Presentacion no encntrada", null);            }
        }catch(NullPointerException npE){
            throw new PresentationNotFoundException("Presentacion no encntrada", null);
        }
    }



	public List<Presentacion> obtenerTodos() {
		return presentacionRepository.findAll();
    }


    public Map<String,String> borrarPresentacionPorId(Long idPresentacion) {
        Map<String,String> responseService = new HashMap<>();
        try{
            presentacionRepository.deleteById(idPresentacion);
            responseService.put("status", "ok");
            responseService.put("mensaje", "Presentacion borrado");
            
        }catch(IllegalArgumentException iaE){
            responseService.put("status", "error");
            responseService.put("mensaje", "Presentacion no encontrado");
            
        }
        return responseService;


	}
    @Autowired
    public PresentacionService(PresentacionRepository presentacionRepository, UserService userService){
        this.presentacionRepository = presentacionRepository;
        this.userService = userService;
    }
}
