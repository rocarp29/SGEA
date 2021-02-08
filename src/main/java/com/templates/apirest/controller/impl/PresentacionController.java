package com.templates.apirest.controller.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import com.templates.apirest.configuracion.Roles;
import com.templates.apirest.model.Evento;
import com.templates.apirest.model.Presentacion;
import com.templates.apirest.model.Usuario;
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
@RequestMapping("/presentaciones")
public class PresentacionController {

	@Autowired
    private EventosService eventoService;
    @Autowired 
    private PresentacionService presentacionService;
    @Autowired
    private UserService userService;


    @ResponseBody
    @PostMapping(value="/crear/{idEvento}", produces=MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> crearPresentacion(@PathVariable Long idEvento, @RequestBody Map<String,String> request){
        Map<String, Object> response = new HashMap<>();
        Long idUsuario = Long.parseLong(request.get("idUsuario"));
        String token = request.get("token");
        Usuario usuarioAutorizante = userService.obtenerUsuarioPorId(idUsuario);
        if(userService.validateToken(usuarioAutorizante, token) && usuarioAutorizante.getRol().equals(Roles.ADMIN)){
            String ponente = request.get("ponente");
            String titulo = request.get("titulo");
            Presentacion presentacion = new Presentacion();
            presentacion.setPonente(ponente);
            presentacion.setTitulo(titulo);
            Evento eventoPresentacion = eventoService.obtenerEvento(idEvento);
            presentacion.setEvento(eventoPresentacion);
            Presentacion presentacionGuardada = presentacionService.crearPresentacion(presentacion);
            if(presentacionGuardada == null){
                response.put("status", "error");
                response.put("token", "ok");
                response.put("error", "Presentacion ya existe con ese titulo y ponente");
                return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);

            }else{
                response.put("status", "ok");
                response.put("token", "ok");
                response.put("presentacion", presentacionGuardada);
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
    @PostMapping(value="/{idEvento}/{idPresentacion}", produces=MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> modificarPresentacion(@PathVariable Long idEvento,
    @PathVariable Long idPresentacion, @RequestBody Map<String,String> request){
        Map<String, Object> response = new HashMap<>();
        Long idUsuario = Long.parseLong(request.get("idUsuario"));
        String token = request.get("token");
        Usuario usuarioAutorizante = userService.obtenerUsuarioPorId(idUsuario);
        if(userService.validateToken(usuarioAutorizante, token) && usuarioAutorizante.getRol().equals(Roles.ADMIN)){
            String ponente = request.get("ponente");
            String titulo = request.get("titulo");
            
            Presentacion presentacionAModificar = presentacionService.obtenerPresentacionPorId(idPresentacion);
            presentacionAModificar.setPonente(ponente);
            presentacionAModificar.setTitulo(titulo);
            Presentacion presentacionGuardada = presentacionService.modificarPresentacion(presentacionAModificar);
            if(presentacionGuardada == null){
                response.put("status", "error");
                response.put("token", "ok");
                response.put("error", "Presentacion ya existe con ese titulo y ponente");
                return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);

            }else{
                response.put("status", "ok");
                response.put("token", "ok");
                response.put("presentacion", presentacionGuardada);
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
    @PostMapping(value="/asistir/{idEvento}/{idPresentacion}", produces=MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> asistirPresentacion(@PathVariable Long idEvento,
    @PathVariable Long idPresentacion, @RequestBody Map<String,String> request){
        Map<String, Object> response = new HashMap<>();
        Long idUsuario = Long.parseLong(request.get("idUsuario"));
        String token = request.get("token");
        Usuario usuarioAutorizante = userService.obtenerUsuarioPorId(idUsuario);
        if(userService.validateToken(usuarioAutorizante, token)){
            Presentacion presentacion = presentacionService.obtenerPresentacionPorId(idEvento);
            Usuario usuario = userService.obtenerUsuarioPorId(idEvento);
            presentacionService.addAsistente(presentacion, usuario);
            response.put("status", "ok");
            response.put("token", "ok");
            return new ResponseEntity<>(response,HttpStatus.OK);
            
        }else{
            response.put("status", "error");
            response.put("token", "error");
            response.put("error", "Token no valido");
            return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);

        }

    }
    @ResponseBody
    @GetMapping(value="/all", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> obtenerTodasPresentaciones(){
        List<Presentacion> presentaciones = presentacionService.obtenerPresentaciones();

        return new ResponseEntity<>(presentaciones,HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping(value="/{idEvento}/{idPresentacion}", produces=MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> borrarPresentacion(@PathVariable Long idEvento,
    @PathVariable Long idPresentacion, @RequestBody Map<String,String> request){
        Map<String, Object> response = new HashMap<>();
        Long idUsuario = Long.parseLong(request.get("idUsuario"));
        String token = request.get("token");
        Usuario usuarioAutorizante = userService.obtenerUsuarioPorId(idUsuario);
        if(userService.validateToken(usuarioAutorizante, token) && usuarioAutorizante.getRol().equals(Roles.ADMIN)){
            try{
                presentacionService.borrarPresentacionPorId(idPresentacion);
                response.put("status", "ok");
                response.put("token", "ok");
                return new ResponseEntity<>(response,HttpStatus.OK);

            }catch(NoSuchElementException nseE){
                response.put("status", "error");
                response.put("token", "ok");
                response.put("error", "Presentacion no existe");
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
