package com.templates.apirest.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.management.relation.Role;

import com.templates.apirest.configuracion.Roles;
import com.templates.apirest.configuracion.exceptions.PasswordException;
import com.templates.apirest.configuracion.exceptions.UserNotFoundException;
import com.templates.apirest.model.Usuario;
import com.templates.apirest.model.dto.UsuarioDTO;
import com.templates.apirest.persistence.impl.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository usuarioRepository;


    public Usuario crearUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizarUsuario(Usuario usuario) throws UserNotFoundException{
        Optional<Usuario> usuarioAModificar = usuarioRepository.findById(usuario.getId());
        if(usuarioAModificar.isPresent()){
            //todo: loger
            System.out.println("Usuario presente - modificando....");
            Usuario usuarioEnModificacion = usuarioAModificar.get();
            usuarioEnModificacion.setNombre(usuario.getNombre());
            usuarioEnModificacion.setPassword(usuario.getPassword());
            usuarioEnModificacion.setRol(usuario.getRol());
            usuarioRepository.save(usuarioEnModificacion);
            return usuarioEnModificacion;
        }else {
            //Log:
            throw new UserNotFoundException("Usuario no encontrado: ID: " + usuario.getId(), null);
        }
    }

    public List<Usuario> obtenerUsuarios(){
        return usuarioRepository.findAll();
    }

    public Usuario obtenerUsuarioPorNombre(String nombre) throws UserNotFoundException{
        
        Optional<Usuario> usuarioEncontrado = obtenerUsuarios().stream().filter(
            user -> user.getNombre().equals(nombre)).findFirst();
            if(usuarioEncontrado.isPresent()){
                return usuarioEncontrado.get();
            }else {
                //Log:
                throw new UserNotFoundException("Usuario no encontrado: Nombre: " + nombre, null);
            }
    }

    public boolean validarPassword(Usuario usuarioAAutenticar, Usuario usuarioEncontrado)
    {
        if(usuarioAAutenticar.getPassword().equals(usuarioEncontrado.getPassword())){
            //Log:
            System.out.println("PasswordO OK");
            return true; 
        }else {
            System.out.println("PasswordO Error");
            return false;
        }
   
    }

    public Map<String,String> autenticarUsuario(Usuario usuario) throws UserNotFoundException, PasswordException{

        Map<String,String> response = new HashMap<String,String>();

        try{
            Usuario usuarioEncontrado = obtenerUsuarioPorNombre(usuario.getNombre());
            boolean authFlag = validarPassword(usuario, usuarioEncontrado);

            if(authFlag){
               response.put("user", usuario.getNombre());
               response.put("auth", "ok");
               response.put("token", usuarioEncontrado.getToken());
               return response;
            }
            else{
                response.put("user", null);
                response.put("auth", "error");
                response.put("error","passwordError");
                response.put("token",null);
                return response;
            }

        }catch(UserNotFoundException unfE){
            //Log:
            response.put("error","userNotFoundError");
            response.put("token","error");
            
            System.out.println("Error al obtener usuario de Base De Datos - UserServiceLayer");
            return response;
        }

    }


    public boolean validateToken(Usuario usuario, String tokenAValidar){
        try{
            Usuario usuarioObtenido = obtenerUsuarioPorNombre(usuario.getNombre());
            return tokenAValidar.equals(usuarioObtenido.getToken());
        }catch(UserNotFoundException unfE){
            return false;
        }
       
    }
    
    public Map<String,String> guardarUsuario(Usuario usuario){
        Map<String, String> response = new HashMap<>();
        try{
            Usuario usuarioEncontrado = obtenerUsuarioPorNombre(usuario.getNombre());
            response.put("status","error");
            response.put("error", "Usuario con nombre "+usuarioEncontrado.getNombre() + " ya existe");
            return response;
        
        }catch(UserNotFoundException unfE){
            //Log:
            usuario.setRol(Roles.USUARIO);
            //TODO: añadir token
            usuario.setToken("token");
            Usuario usuarioRespuesta = usuarioRepository.save(usuario);
            response.put("status","ok");
            response.put("token",usuarioRespuesta.getToken());
            response.put("idUsuario",usuarioRespuesta.getId().toString());
            return response;
        }

    }


    public Map<String,String> modificarUsuario(Usuario usuario){
        Map<String, String> response = new HashMap<String,String>();
        try{
            Usuario usuarioEncontrado = obtenerUsuarioPorNombre(usuario.getNombre());
            usuarioEncontrado.setPassword(usuario.getPassword());
            usuarioRepository.save(usuarioEncontrado);
            response.put("status","ok");
            response.put("error", "");
            return response;
        
        }catch(UserNotFoundException unfE){
            //Log:
            
            response.put("status","error");
            response.put("error","No existe usuario con nombre asociado");
            //TODO: añadir token
            
            return response;
        }

    }

	public String getTokenPorNombre(Usuario usuario) {
        Usuario usuarioObtenido = obtenerUsuarioPorNombre(usuario.getNombre());
        return usuarioObtenido.getToken();
    }

    
	public Roles getRolUsuario(Usuario usuario) {
        Usuario usuarioObtenido = obtenerUsuarioPorNombre(usuario.getNombre());
        return usuarioObtenido.getRol();
    }

	public Usuario obtenerUsuarioPorId(Long idUsuario) {
        return usuarioRepository.findById(idUsuario).orElse(null);
    }
    
    public Map<String,String> borrarUsuario(Long idUsuarioABOrrar, Long  idAutorizante, String validateToken) 
    {
        Map<String, String> response = new HashMap<>();

        try{
            Usuario autorizante = obtenerUsuarioPorId(idAutorizante);
            Usuario usuarioABorrar = obtenerUsuarioPorId(idUsuarioABOrrar);
            boolean validToken = validateToken(autorizante, validateToken);
            if(validToken){
                if(autorizante.getRol() == Roles.ADMIN){
                    response.put("token", "ok");
                    usuarioRepository.delete(usuarioABorrar);
                }else {
                    response.put("token", "ok");
                    response.put("status", "error");    
                    response.put("error", "Error  - Usuario no tiene el rol de admin");
                }
            }else{
                response.put("token", "error");
                response.put("status", "error");
                response.put("error", "Error  - Tokens no validos" +idUsuarioABOrrar);
                
            }
        }catch(IllegalArgumentException iae){
            response.put("status", "error");
            response.put("error", "Error  - No existe usuario con id "+idUsuarioABOrrar);
        } 
        return response;
    }

}
