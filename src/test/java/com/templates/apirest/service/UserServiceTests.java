package com.templates.apirest.service;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.templates.apirest.ApirestApplication;
import com.templates.apirest.configuracion.Roles;
import com.templates.apirest.model.Usuario;
import com.templates.apirest.model.dto.UsuarioDTO;
import com.templates.apirest.persistence.impl.UserRepository;
import com.templates.apirest.service.impl.UserService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;



// @Test
// @WebAppConfiguration
// public class YourControllerTest extends AbstractTestNGSpringContextTests {
//     @Autowired
//     private WebApplicationContext wac;

//     private MockMvc mockMvc;


//     @Test
//     public void getEmailVerificationTest() throws Exception {
//         this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

//         this.mockMvc.perform(get("/home")
//                 .accept(MediaType.ALL))
//                 .andExpect(status().isOk())
//                 .andExpect(view().name("home/index"));
//     }
// }


@RunWith(SpringRunner.class)
@SpringBootTest(classes=ApirestApplication.class)
public class UserServiceTests extends AbstractTestNGSpringContextTests {

    // @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @MockBean                           
    private UserService userService;

    @MockBean                           
    private UserRepository userRepository;
    @Mock
    private ModelMapper modelMapper;

    private ObjectMapper om;

    private Usuario userPrueba;
    private UsuarioDTO userDTO;

     @BeforeEach
     public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        this.om = new ObjectMapper();
        this.userDTO = new UsuarioDTO("testUsuario","testUsuario",Roles.ADMIN,"token");
        this.userPrueba = new Usuario("testUsuario","testUsuario",Roles.ADMIN,"token");


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
    public void testResponseGuardadoUsuarioOK() throws Exception{

        Map<String, String> response=  userService.guardarUsuario(this.userPrueba);
        String status = response.get("status");
        Assertions.assertEquals("ok",status );


        // UsuarioDTO userDTO = new UsuarioDTO("prueba","prueba",Roles.ADMIN,"token");
        // Usuario userPrueba = new Usuario("prueba","prueba",Roles.ADMIN,"token");
        // String jsonRequest = om.writeValueAsString(userPrueba);

        // MvcResult result = mockMvc.perform(post("/user/auth").content(jsonRequest)
        //         .contentType(MediaType.APPLICATION_JSON_VALUE))
        //         .andExpect(status().isOk()).andReturn();
        // String responseContent = result.getResponse().getContentAsString();
        // ResponseEntity<Object> response = om.readValue(responseContent, ResponseEntity.class);
        // assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
           
    }
    @Test
    public void testGuardadoTokenNuevo() throws Exception{

        Map<String, String> response=  userService.guardarUsuario(this.userPrueba);
        String tokenServicio = response.get("token");
        Long idUsuarioCreado = Long.parseLong(response.get("idUsuario"));
        String tokenRepositorio = userRepository.getOne(idUsuarioCreado).getToken();
        Assertions.assertEquals(tokenServicio,tokenRepositorio );
           
    }

    @Test
    public void testResponseGuardadoUsuarioError() throws Exception{
        //pruebo guardado no duplicado
        userService.guardarUsuario(this.userPrueba);
        Map<String, String> response=  userService.guardarUsuario(this.userPrueba);
        String status = response.get("status");
        Assertions.assertEquals("error",status );


           
    }



    
}
