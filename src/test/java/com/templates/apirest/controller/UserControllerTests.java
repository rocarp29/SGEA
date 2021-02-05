package com.templates.apirest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.templates.apirest.configuracion.Roles;
import com.templates.apirest.controller.impl.UserController;
import com.templates.apirest.model.Usuario;
import com.templates.apirest.model.dto.UsuarioDTO;
import com.templates.apirest.service.impl.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


// @RunWith(SpringRunner.class)
// @SpringBootTest
@WebMvcTest(controllers = UserController.class)
@ActiveProfiles("test")
public class UserControllerTests {

        // @Autowired
        private MockMvc mockMvc;
        @Autowired
        private WebApplicationContext context;

        // @MockBean
        // private UserController userController ; 
         @MockBean                           
        private UserService userService;
        @Mock
        private ModelMapper modelMapper;

        private ObjectMapper om;
    
         @BeforeEach
         public void setUp() {
            mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
            this.om = new ObjectMapper();

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
        public void testGuardadoUsuarioHttpOK() throws Exception{
            UsuarioDTO userDTO = new UsuarioDTO("prueba","prueba",Roles.ADMIN,"token");
            Usuario userPrueba = new Usuario("prueba","prueba",Roles.ADMIN,"token");
            String jsonRequest = om.writeValueAsString(userPrueba);

            MvcResult result = mockMvc.perform(post("/user/auth").content(jsonRequest)
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk()).andReturn();
            String responseContent = result.getResponse().getContentAsString();
            ResponseEntity<Object> response = om.readValue(responseContent, ResponseEntity.class);
            assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
               
        }

        /*@Test
        public void testGuardadoUsuarioCorrecto() throws Exception{
            Usuario userPrueba = new Usuario("pruebaMock","prueba",Roles.ADMIN,"token");
            UsuarioDTO userDTO = new UsuarioDTO("pruebaMock","prueba",Roles.ADMIN,"token");
            userController.guardarUsuarioNuevo(userDTO);
            ResponseEntity<Object> responseMock = userController.validarCredenciales(userDTO);
            
            JSONObject obj = new JSONObject(responseMock.getBody().toString());
            String nombreUsuario = obj.getString("nombre");
            System.out.println("NombreUsuario Test obtenido: " + nombreUsuario);
            MatcherAssert.assertThat(nombreUsuario, equals(userDTO.getNombre()));
                
        }*/
    
    }
        

