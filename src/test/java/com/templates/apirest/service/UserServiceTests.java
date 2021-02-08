package com.templates.apirest.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.templates.apirest.ApirestApplication;
import com.templates.apirest.configuracion.Roles;
import com.templates.apirest.model.Usuario;
import com.templates.apirest.model.dto.UsuarioDTO;
import com.templates.apirest.persistence.impl.UserRepository;
import com.templates.apirest.service.impl.UserService;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;



// @RunWith(SpringRunner.class)
@RunWith(MockitoJUnitRunner.class)

// @WebAppConfiguration 
// @SpringBootTest(classes=ApirestApplication.class)
public class UserServiceTests extends AbstractTestNGSpringContextTests {

    // @Autowired
    // private MockMvc mockMvc;
    // @Autowired
    // private WebApplicationContext context;

    @InjectMocks                           
    private UserService userService;

    @Mock                         
    private UserRepository userRepository;
    @Mock
    private ModelMapper modelMapper;

    private ObjectMapper om;

    private Usuario userPrueba;
    private Optional<Usuario> optionalUserPrueba;
    private Usuario userPrueba2;
    private List<Usuario> userListaPrueba;
    private UsuarioDTO userDTO;
    

     @BeforeEach
     public void setUp() {
        // mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        this.om = new ObjectMapper();
        MockitoAnnotations.openMocks(this); // this is needed for inititalizytion of mocks, if you use @Mock 
        this.userDTO = new UsuarioDTO("testUsuario","testUsuario",Roles.ADMIN,"token");
        this.userPrueba = new Usuario("testUsuario","testUsuario",Roles.ADMIN,"token");
        this.userPrueba2 = new Usuario("testUsuario2","testUsuario2",Roles.ADMIN,"token");
        this.userService = new UserService(userRepository);
        this.userListaPrueba = new ArrayList<Usuario>();
        this.userListaPrueba.add(userPrueba);
        this.userListaPrueba.add(userPrueba2);
        
        
        userPrueba.setId((long)10);
        when(userRepository.save(this.userPrueba)).thenReturn(userPrueba);
        when(userRepository.getOne((long) 10)).thenReturn(userPrueba);
        when(userRepository.getOne((long) 11)).thenReturn(userPrueba2);
        when(userRepository.findAll()).thenReturn(userListaPrueba);
        when(userRepository.findById((long)10)).thenReturn(Optional.of(userPrueba));


    }

    // public void setUp() throws Exception{
    //     MockitoAnnotations.openMocks(this);
    //     mockMvc=MockMvcBuilders.standaloneSetup(userController).build();
    // }

    /**
     * Testeamos codigo de reponse entity ok value. 
     * @throws Exception
     */
    @Test
    void testResponseGuardadoUsuarioOK() throws Exception{

        Map<String, String> response=  userService.guardarUsuario(this.userPrueba);
        String status = response.get("status");
        Assertions.assertEquals("ok",status );
        
    }



    
    @Test
    void testGuardadoTokenNuevo() throws Exception{

        Map<String, String> response=  userService.guardarUsuario(this.userPrueba);
        String tokenServicio = response.get("token");
        Long idUsuarioCreado = Long.parseLong(response.get("idUsuario"));
        String tokenRepositorio = userRepository.getOne(idUsuarioCreado).getToken();
        Assertions.assertEquals(tokenServicio,tokenRepositorio );
           
    }

    // @Test
    // public void testResponseGuardadoUsuarioError() throws Exception{
        
    //     when(userRepository.findAll().stream().filter(p -> true).
    //      collect(Collectors.toList()).get(0))
    //         .thenReturn(userPrueba);
        
    //     Map<String, String> response=  userService.guardarUsuario(this.userPrueba);
    //     String status = response.get("status");
    //     Assertions.assertEquals("error",status );


           
    // }

    @Test
    void obtenerUsuarioPorNombreTest(){
        
        Usuario usuarioEncontrado = userService.obtenerUsuarioPorNombre("testUsuario");
        
        Assertions.assertEquals(userPrueba, usuarioEncontrado);
        
    }

    @Test
    void validarPasswordTest(){
        Assertions.assertTrue(userService.validarPassword(this.userPrueba, this.userPrueba));

        
        
    }

    
    @Test
    void validarPasswordMalTest(){
        Assertions.assertFalse(userService.validarPassword(this.userPrueba, this.userPrueba2));
       
    }
    @Test
    void autenticarUsuarioTest(){

        Map<String, String> respuesta = userService.autenticarUsuario(this.userPrueba);
        String status = respuesta.get("auth");
        Assertions.assertEquals("ok", status);
    } 

    @Test
    //TODO: PENDIENTE. 
    void autenticarUsuarioFallidoTest(){
        mock(UserService.class);
        when(userRepository.findAll()).thenReturn(this.userListaPrueba);

        // when(userService.obtenerUsuarioPorNombre("testUsuario")).thenReturn(this.userPrueba);

        Usuario userPruebaMalPassword = this.userPrueba;
        userPruebaMalPassword.setPassword("error");
        Map<String, String> respuesta = userService.autenticarUsuario(userPruebaMalPassword);
        String status = respuesta.get("auth");
        Assertions.assertEquals("error", status);
    } 

    @Test
    void validateTokenTest(){
        Assertions.assertTrue(userService.validateToken(this.userPrueba, "token"));
    }
    
    
    @Test
    void validateTokenFailTest(){
        Assertions.assertFalse(userService.validateToken(this.userPrueba, "tokenErroneo"));
    }

    @Test
    void ModificarUsuarioTest(){
        userPrueba.setPassword("passwordDistinta");
        Map<String, String> response=  userService.modificarUsuario(userPrueba);
        String status = response.get("status");
        Assertions.assertEquals("ok",status );
    }

    
    @Test
    void getTokenPorNombreTest(){

        Assertions.assertEquals(userPrueba.getToken(),userService.getTokenPorNombre(this.userPrueba));
    }

    @Test
    void getRolUsuarioTest(){
        Assertions.assertEquals(userPrueba.getRol(),userService.getRolUsuario(this.userPrueba));
    }


    @Test
    void obtenerUsuarioPorIdTest(){
        Usuario usuario = userService.obtenerUsuarioPorId((long) 10);
        Assertions.assertEquals(userPrueba, usuario);
        
    }

    @Test
    void borrarUsuario(){
        Map<String,String> response = userService.borrarUsuario((long) 11,(long) 10,
        "token");
        String status = response.get("status");
        Assertions.assertEquals("ok",status );
        
    }
    


    

    
}
