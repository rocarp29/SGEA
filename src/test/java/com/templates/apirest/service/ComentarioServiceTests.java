package com.templates.apirest.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.templates.apirest.ApirestApplication;
import com.templates.apirest.configuracion.Roles;
import com.templates.apirest.configuracion.exceptions.CommentNotFoundException;
import com.templates.apirest.configuracion.exceptions.EventNotFoundException;
import com.templates.apirest.model.Comentario;
import com.templates.apirest.model.Evento;
import com.templates.apirest.model.Usuario;

import com.templates.apirest.persistence.impl.ComentarioRepository;
import com.templates.apirest.persistence.impl.ComentarioRepository;
import com.templates.apirest.service.impl.ComentarioService;
import com.templates.apirest.service.impl.EventoService;
import com.templates.apirest.service.impl.UserService;
import com.templates.apirest.service.impl.ComentarioService;

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


@RunWith(MockitoJUnitRunner.class)
public class ComentarioServiceTests extends AbstractTestNGSpringContextTests{
    @InjectMocks                           
    private ComentarioService comentarioService;

    @Mock                         
    private ComentarioRepository comentarioRepository;
    @Mock                         
    private UserService userService;
    @Mock
    private EventoService eventoService;
    

    @Mock
    private ModelMapper modelMapper;


    private ObjectMapper om;

    private Comentario comentarioPrueba;
    private Optional<Comentario> optionalComentarioPrueba;
    private Comentario comentarioPrueba2;
    private List<Comentario> comentarioListaPrueba;
    private LocalDate fechaActual;
    private Usuario userPrueba;
    private Evento eventoPrueba;

    @BeforeEach
     public void setUp() {
         this.fechaActual = LocalDate.now();
        
        this.om = new ObjectMapper();
        MockitoAnnotations.openMocks(this); // this is needed for inititalizytion of mocks, if you use @Mock 
        this.userPrueba = new Usuario("testUsuario","testUsuario",Roles.ADMIN,"token");
        this.eventoPrueba = new Evento("eventoPrueba",LocalDateTime.now());
        this.eventoPrueba.setId((long)15);
        this.comentarioPrueba = new Comentario(fechaActual,"comentarioPrueba", this.eventoPrueba, this.userPrueba);
        this.comentarioPrueba2 = new Comentario(fechaActual,"comentarioPrueba2",this.eventoPrueba, this.userPrueba);
        this.eventoPrueba.addComentario(comentarioPrueba);
        this.eventoPrueba.addComentario(comentarioPrueba2);
        this.comentarioService = new ComentarioService(eventoService,userService,comentarioRepository);
        

        this.comentarioListaPrueba = new ArrayList<Comentario>();
        this.comentarioListaPrueba.add(comentarioPrueba);
        this.comentarioListaPrueba.add(comentarioPrueba2);

        
        comentarioPrueba.setId((long)10);
        when(comentarioRepository.save(this.comentarioPrueba)).thenReturn(comentarioPrueba);
        when(comentarioRepository.getOne((long) 10)).thenReturn(comentarioPrueba);
        when(comentarioRepository.getOne((long) 11)).thenReturn(comentarioPrueba2);
        when(comentarioRepository.findAll()).thenReturn(comentarioListaPrueba);
        when(comentarioRepository.findById((long)10)).thenReturn(Optional.of(comentarioPrueba));
        when(comentarioService.obtenerComentarios().stream().filter(
            comentario -> this.eventoPrueba.getId().equals(comentario.getId()))
            .collect(Collectors.toList()))
            .thenReturn(this.comentarioListaPrueba);
        

    }

    @Test
    void guardadoComentarioTest(){
        Comentario comentarioGuardado =  comentarioService.crearComentario(this.comentarioPrueba);
        
        Assertions.assertEquals(this.comentarioPrueba,comentarioGuardado );
        
    }

    
    @Test
    void obtenerComentariosTest(){
        List<Comentario> comentariosGuardado =  comentarioService.obtenerComentarios();
        Assertions.assertEquals(this.comentarioListaPrueba, comentariosGuardado);        
    }

    //TODO:
    @Test
    void obtenerComentariosPorEventoTest(){
        List<Comentario> comentariosEvento =  comentarioService.obtenerComentariosPorEvento(this.eventoPrueba);
        Assertions.assertEquals(this.comentarioListaPrueba, comentariosEvento);        
    }
    
    @Test
    
    void obtenerComentariosPorEventoFailTest(){
        Evento eventoError = new Evento("eventoPruebaError",LocalDateTime.now());
        Assertions.assertThrows(CommentNotFoundException.class,() -> comentarioService.obtenerComentariosPorEvento(eventoError));        
    }
    //TODO
    @Test
    void obtenerComentarioPorUsuarioEventoTest() {
        Comentario comentarioEvento =  comentarioService.obtenerComentarioPorUsuarioEvento(this.eventoPrueba, this.userPrueba);
        Assertions.assertEquals(this.comentarioListaPrueba, comentarioEvento);        
    }
    
    @Test
    void borrarComentarioPorIdTest(){
        Map<String,String> responseService = comentarioService.borrarComentarioPorId((long)10);
        Assertions.assertEquals("ok", responseService.get("status"));        
    }
        

}



