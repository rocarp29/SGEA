package com.templates.apirest.controller.impl;

import java.util.ArrayList;
import java.util.List;

import com.templates.apirest.model.Evento;
import com.templates.apirest.model.Usuario;
import com.templates.apirest.model.dto.GuardarEventoDTO;
import com.templates.apirest.service.impl.EventosService;
import com.templates.apirest.service.impl.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
        if(usuarioValido){
            eventoService.crearEvento(evento);
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Error", HttpStatus.OK);
        }
    }

    @ResponseBody
    @GetMapping(value="/all", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> obtenerTodosEventos(){
        List<Evento> eventos = eventoService.obtenerTodos();
        return new ResponseEntity<>(eventos,HttpStatus.OK);
    }


}
