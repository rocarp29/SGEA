package com.templates.apirest.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.templates.apirest.configuracion.exceptions.PasswordException;
import com.templates.apirest.configuracion.Roles;
import com.templates.apirest.configuracion.exceptions.CommentNotFoundException;
import com.templates.apirest.configuracion.exceptions.EventNotFoundException;
import com.templates.apirest.model.Comentario;
import com.templates.apirest.model.Evento;
import com.templates.apirest.model.Usuario;
import com.templates.apirest.model.dto.EventoDTO;
import com.templates.apirest.persistence.impl.ComentarioRepository;
import com.templates.apirest.persistence.impl.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComentarioService {
    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired 
    private UserService userService;

    
    @Autowired 
    private EventosService eventoService;

    public Comentario crearComentario(Comentario comentario){

        return comentarioRepository.save(comentario);

    }

    public Comentario modificarComentario(Comentario comentario){

        return comentarioRepository.save(comentario);

    }

    
    public List<Comentario> obtenerComentarios(){
        return comentarioRepository.findAll();
    }

    public List<Comentario> obtenerComentariosPorEvento(Evento evento) throws EventNotFoundException{
        List<Comentario> comentariosEncontrados = obtenerComentarios().stream().filter(
            comentario -> evento.getId().equals(comentario.getId())).collect(Collectors.toList());
            if(!comentariosEncontrados.isEmpty() ){
                return comentariosEncontrados;
            }else {
                //Log:
                throw new CommentNotFoundException("Comentarios para evento no encontrado: Nombre: " + evento.getNombre(), null);
            }
    }


	public Comentario obtenerComentarioPorUsuarioEvento(Evento evento, Usuario usuario) {
        return obtenerComentarios().stream().filter(comentario -> comentario.getEvento().getId().equals(evento.getId()) 
        && comentario.getUsuario().getId().equals(usuario.getId())).findFirst().orElse(null);
       
    }

	public Map<String,String> borrarComentarioPorId(Long idComentario) {
        Map<String,String> responseService = new HashMap<>();
        try{
            comentarioRepository.deleteById(idComentario);
            responseService.put("status", "ok");
            responseService.put("mensaje", "Comentario borrado");
            
        }catch(IllegalArgumentException iaE){
            responseService.put("status", "error");
            responseService.put("mensaje", "Comentario no encontrado");
            
        }
        return responseService;


	}


}
