package com.templates.apirest.controller.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.websocket.server.PathParam;

import com.templates.apirest.configuracion.exceptions.UserNotFoundException;
import com.templates.apirest.model.Usuario;
import com.templates.apirest.model.dto.UsuarioDTO;
import com.templates.apirest.service.impl.UserService;

import org.modelmapper.ModelMapper;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import bsh.ParseException;

@RestController 
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;



    @ResponseBody
	@PostMapping(value="/auth", produces=MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<Object> validarCredenciales(@RequestBody UsuarioDTO user) {
        ModelMapper modelMapper = new ModelMapper();	
        Usuario usuario = modelMapper.map(user,Usuario.class);
        
        Map<String,String> response = userService.autenticarUsuario(usuario);
        if(response.get("auth").equals("ok")){
            response.put("login", "OK");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.put("login", "Error - Usuario o contraseña incorrecta");
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
        

    }

    @ResponseBody
	@PostMapping(value="/registrar", produces=MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> guardarUsuarioNuevo(@RequestBody UsuarioDTO usuarioDTO){
        ModelMapper modelMapper = new ModelMapper();	
        Usuario usuario = modelMapper.map(usuarioDTO,Usuario.class);
        Map<String, String> response = userService.guardarUsuario(usuario);
        if(!response.get("status").equals("ok")){
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }else{
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        
    }

    @ResponseBody
	@GetMapping(value="/all", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllUsuarios(){

        List<Usuario> listaUsuarios = userService.obtenerUsuarios();
        
        return new ResponseEntity<>(listaUsuarios, HttpStatus.OK);
        
    }
    
    @ResponseBody
	@GetMapping(value="/{idUsuario}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getUsuario(@PathVariable Long idUsuario){
        Usuario usuario = userService.obtenerUsuarioPorId(idUsuario);
        Map<String, Object> response = new HashMap<>();
        if(usuario != null){
            Map<String,Object> usuarioResponse = new HashMap<>();
            usuarioResponse.put("id", usuario.getId());
            usuarioResponse.put("nombre", usuario.getNombre());
            usuarioResponse.put("rol",usuario.getRol());
            response.put("status", "ok");
            response.put("mensaje", "Usuario creado");
            response.put("usuario", usuarioResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            response.put("status", "error");
            response.put("error", "Usuario no encontrado. ID: " +idUsuario);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        
    }

    @ResponseBody
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getUsuarioByNombre(@RequestParam(value="nombre", required=false)  String nombre){
        Usuario usuario = new Usuario();
        Map<String, Object> response = new HashMap<>();
        Map<String,Object> usuarioResponse = new HashMap<>();
        try{
            usuario = userService.obtenerUsuarioPorNombre(nombre);
            usuarioResponse.put("id", usuario.getId());
            usuarioResponse.put("nombre", usuario.getNombre());
            usuarioResponse.put("rol",usuario.getRol());
            response.put("status", "ok");
            response.put("usuario", usuarioResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(UserNotFoundException unfE){
            response.put("status", "error");
            response.put("error", "Usuario no encontrado. ID: " +nombre);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        
    }


    @ResponseBody
    @DeleteMapping(value="/{idUsuario}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> borrarUsuario(@PathVariable Long idUsuario, @RequestBody Map<String,String> request){
        Map<String, String> response = new HashMap<>();
        try{
            Long idUsuarioAutorizante = Long.parseLong(request.get("usuarioId"));
            String token = request.get("token");

            response = userService.borrarUsuario(idUsuario, idUsuarioAutorizante, token);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(NumberFormatException nfE){
            response.put("status", "error");
            response.put("error", "Usuario a borrar no encontrado " + idUsuario + " ");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }catch(NullPointerException npE){
            response.put("status", "error");
            response.put("error", "Faltan datos claves: ID " + idUsuario);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        
        
    }

    

    

}
