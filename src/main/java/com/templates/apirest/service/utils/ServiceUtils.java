package com.templates.apirest.service.utils;

import com.templates.apirest.model.Usuario;
import com.templates.apirest.model.dto.UsuarioDTO;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

public final class ServiceUtils {
    private ModelMapper modelMapper;
    private ServiceUtils(){
        this.modelMapper = new ModelMapper();	
    }
    
    
    public Usuario parseToUsuario(UsuarioDTO usuarioDTO){
        return modelMapper.map(usuarioDTO,Usuario.class);
        
    }
    public UsuarioDTO parseToUsuarioDTO(Usuario usuario){
        return modelMapper.map(usuario,UsuarioDTO.class);
        
    }
}
