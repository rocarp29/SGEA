package com.templates.apirest.controller.impl;

import java.util.List;
import java.util.Map;

import com.templates.apirest.model.Usuario;
import com.templates.apirest.model.dto.UsuarioDTO;
import com.templates.apirest.service.impl.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
	@PostMapping(value="/register", produces=MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
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
	@PostMapping(value="/all", produces=MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllUsuarios(){

        List<Usuario> listaUsuarios = userService.obtenerUsuarios();
        
        return new ResponseEntity<>(listaUsuarios, HttpStatus.OK);
        
    }
    

    

}
