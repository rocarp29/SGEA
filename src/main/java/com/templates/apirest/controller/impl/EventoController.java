package com.templates.apirest.controller.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.templates.apirest.configuracion.Roles;
import com.templates.apirest.model.Evento;
import com.templates.apirest.model.Usuario;
import com.templates.apirest.model.dto.GuardarEventoDTO;
import com.templates.apirest.service.impl.EventosService;
import com.templates.apirest.service.impl.UserService;

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
@RequestMapping("/eventos")
public class EventoController {

	@Autowired
    private EventosService eventoService;
    
    @Autowired
    private UserService userService;


    @ResponseBody
    @PostMapping(value="/guardar", produces=MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> guardarEvento(@RequestBody GuardarEventoDTO guardarEventoDTO){
        Usuario usuario = guardarEventoDTO.getUsuario();
        Evento evento = guardarEventoDTO.getEvento();
        boolean usuarioValido = userService.validateToken(usuario, guardarEventoDTO.getToken());
        Map<String, Object> response = new HashMap<>();

        if(usuarioValido && usuario.getRol().equals(Roles.ADMIN)){
            Evento eventoGuardado = eventoService.crearEvento(evento);
            response.put("status", "ok");
            response.put("mensaje", "Usuario creado");
            response.put("evento", eventoGuardado);   
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        }else {
            return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
        }
    }

    @ResponseBody
    @GetMapping(value="/all", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> obtenerTodosEventos(){
        List<Evento> eventos = eventoService.obtenerTodos();

        return new ResponseEntity<>(eventos,HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping(value="/{idEvento}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> borrarEvento(@PathVariable Long idEvento, @RequestBody Map<String,String> request){
        Map<String, String> response = new HashMap<>();
        try{
            Long idUsuarioAutorizante = Long.parseLong(request.get("usuarioId"));
            String token = request.get("token");
            Usuario usuarioAutorizante = userService.obtenerUsuarioPorId(idUsuarioAutorizante);
            if(usuarioAutorizante.getRol().equals(Roles.ADMIN) && 
                userService.validateToken(usuarioAutorizante, token)){
                    response = eventoService.borrarEvento(idEvento);

                }else{
                    response.put("status", "error");
                    response.put("token", "error");
                    response.put("error", "Token o rol de usuario no validos ");
                    return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);

                }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(NumberFormatException nfE){
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }catch(NullPointerException npE){
            response.put("status", "error");
            response.put("error", "Faltan datos claves: ID " + idEvento);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        
        
    }




}
