package com.templates.apirest.controller.impl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import com.templates.apirest.configuracion.Roles;
import com.templates.apirest.model.Comentario;
import com.templates.apirest.model.Evento;
import com.templates.apirest.model.Presentacion;
import com.templates.apirest.model.Usuario;
import com.templates.apirest.service.impl.ComentarioService;
import com.templates.apirest.service.impl.EventosService;
import com.templates.apirest.service.impl.PresentacionService;
import com.templates.apirest.service.impl.UserService;

import org.aspectj.weaver.patterns.PerCflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping("/comentarios")
public class ComentarioController {

	@Autowired
    private EventosService eventoService;
    @Autowired 
    private PresentacionService presentacionService;
    @Autowired
    private UserService userService;
    @Autowired
    private ComentarioService comentarioService; 


    @ResponseBody
    @PostMapping(value="/{idEvento}", produces=MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> comentar(@PathVariable Long idEvento, @RequestBody Map<String,String> request){
        Map<String, Object> response = new HashMap<>();
        Long idUsuario = Long.parseLong(request.get("idUsuario"));
        String token = request.get("token");
        Usuario usuarioAutorizante = userService.obtenerUsuarioPorId(idUsuario);
        if(userService.validateToken(usuarioAutorizante, token)){
            String comentario = request.get("comentario");
            Evento evento = eventoService.obtenerEvento(idEvento);
            Usuario usuario = userService.obtenerUsuarioPorId(Long.parseLong(request.get("idUsuario")));
            Comentario comentarioObject = new Comentario(LocalDate.now(),comentario, evento,  usuario);
            
            Comentario comentarioCreado = comentarioService.crearComentario(comentarioObject);
            if(comentarioCreado == null){
                response.put("status", "error");
                response.put("token", "ok");
                response.put("error", "Comentario ya existe con ese titulo y ponente");
                return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);

            }else{
                response.put("status", "ok");
                response.put("token", "ok");
                response.put("comentario", comentarioCreado);
                return new ResponseEntity<>(response,HttpStatus.OK);
            }

        }else{
            response.put("status", "error");
            response.put("error", "Rol o token no valido");
            response.put("token", "error");
            return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
        }
    }

    @ResponseBody
    @PostMapping(value="/modificar/{idEvento}", produces=MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> modificarComentario(@PathVariable Long idEvento,
    @RequestBody Map<String,String> request){
        Map<String, Object> response = new HashMap<>();
        Long idUsuario = Long.parseLong(request.get("idUsuario"));
        String token = request.get("token");
        Usuario usuarioAutorizante = userService.obtenerUsuarioPorId(idUsuario);
        if(userService.validateToken(usuarioAutorizante, token) ){
            String comentario = request.get("comentario");
            Evento evento = eventoService.obtenerEvento(idEvento);
            Usuario usuario = userService.obtenerUsuarioPorId(Long.parseLong(request.get("idUsuario")));
            
            Comentario comentarioAModificar = comentarioService.obtenerComentarioPorUsuarioEvento(evento,usuario);
            comentarioAModificar.setContenido(comentario);
            
            Comentario comentarioGuardado = comentarioService.modificarComentario(comentarioAModificar);
            if(comentarioGuardado == null){
                response.put("status", "error");
                response.put("token", "ok");
                response.put("error", "comentario ya existe con ese titulo y ponente");
                return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);

            }else{
                response.put("status", "ok");
                response.put("token", "ok");
                response.put("comentario", comentarioGuardado);
            }
            return new ResponseEntity<>(response,HttpStatus.OK);

        }else{
            response.put("status", "error");
            response.put("error", "Rol o token no valido");
            response.put("token", "error");
            return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
        }
    }
 

    @ResponseBody
    @GetMapping(value="/all", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> obtenerComentarios(){
        List<Comentario> comentarios = comentarioService.obtenerComentarios();

        return new ResponseEntity<>(comentarios,HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping(value="/{idComentario}", produces=MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> borrarPresentacion(@PathVariable Long idComentario,
    @RequestBody Map<String,String> request){
        Map<String, String> response = new HashMap<>();
        Long idUsuario = Long.parseLong(request.get("idUsuario"));
        String token = request.get("token");
        Usuario usuarioAutorizante = userService.obtenerUsuarioPorId(idUsuario);
        if(userService.validateToken(usuarioAutorizante, token) ){
            try{
                response = comentarioService.borrarComentarioPorId(idComentario);
                response.put("token", "ok");
                return new ResponseEntity<>(response,HttpStatus.OK);

            }catch(NoSuchElementException nseE){
                response.put("status", "error");
                response.put("token", "ok");
                response.put("error", "Comentario no existe");
                return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);

            }
        }else{
            response.put("status", "error");
            response.put("error", "Rol o token no valido");
            response.put("token", "error");
            return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);


        }
         
            

        
    }




}
