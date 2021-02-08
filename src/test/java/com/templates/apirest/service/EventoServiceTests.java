package com.templates.apirest.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
import com.templates.apirest.configuracion.exceptions.EventNotFoundException;
import com.templates.apirest.model.Evento;
import com.templates.apirest.model.Usuario;
import com.templates.apirest.model.dto.EventoDTO;
import com.templates.apirest.persistence.impl.EventoRepository;
import com.templates.apirest.persistence.impl.EventoRepository;
import com.templates.apirest.service.impl.EventoService;
import com.templates.apirest.service.impl.UserService;
import com.templates.apirest.service.impl.EventoService;

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
public class EventoServiceTests extends AbstractTestNGSpringContextTests {

    @InjectMocks                           
    private EventoService EventoService;

    @Mock                         
    private EventoRepository eventoRepository;
    @Mock                         
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;

    private ObjectMapper om;

    private Evento eventoPrueba;
    private Optional<Evento> optionalEventoPrueba;
    private Evento eventoPrueba2;
    private List<Evento> eventoListaPrueba;
    private LocalDateTime fechaActual;
    private Usuario userPrueba;

    

     @BeforeEach
     public void setUp() {
        // mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        this.om = new ObjectMapper();
        MockitoAnnotations.openMocks(this); // this is needed for inititalizytion of mocks, if you use @Mock 
        this.eventoPrueba = new Evento("eventoPrueba",LocalDateTime.now());
        this.eventoPrueba = new Evento("eventoPrueba2",LocalDateTime.now());
        this.EventoService = new EventoService(userService,eventoRepository);
        this.eventoListaPrueba = new ArrayList<Evento>();
        this.eventoListaPrueba.add(eventoPrueba);
        this.eventoListaPrueba.add(eventoPrueba2);
        this.userPrueba = new Usuario("testUsuario","testUsuario",Roles.ADMIN,"token");

        
        eventoPrueba.setId((long)10);
        when(eventoRepository.save(this.eventoPrueba)).thenReturn(eventoPrueba);
        when(eventoRepository.getOne((long) 10)).thenReturn(eventoPrueba);
        when(eventoRepository.getOne((long) 11)).thenReturn(eventoPrueba2);
        when(eventoRepository.findAll()).thenReturn(eventoListaPrueba);
        when(eventoRepository.findById((long)10)).thenReturn(Optional.of(eventoPrueba));
        this.fechaActual = LocalDateTime.now();


    }

    @Test
    void responseGuardadoEventoOK(){

        Map<String, String> response=  EventoService.guardarEvento(this.eventoPrueba);
        String status = response.get("status");
        Assertions.assertEquals("ok",status );
        
    }

    @Test
    void responseActualizarEventoOK(){
        this.eventoPrueba.setFecha(this.fechaActual.plusYears(2));
        Evento eventoActualizado=  EventoService.actualizarEvento(this.eventoPrueba);
        Assertions.assertEquals(this.fechaActual.plusYears(2),eventoActualizado.getFecha() );
    }

    
    @Test
    void responseActualizarEventoFailTest(){
        this.eventoPrueba.setFecha(this.fechaActual.plusYears(2));
        Evento eventoNoEncontrado = new Evento("eventoError",this.fechaActual.plusYears(2));

        
        Assertions.assertThrows(EventNotFoundException.class,
            () -> EventoService.actualizarEvento(eventoNoEncontrado) );
    }


    @Test
    void modificarEventotest(){

        Map<String, String> response=  EventoService.guardarEvento(this.eventoPrueba);
        String status = response.get("status");
        Assertions.assertEquals("ok",status );
        
    }

    
    @Test
    void obtenerEventosTest(){

        Assertions.assertEquals(this.eventoListaPrueba,EventoService.obtenerEventos() );
        
    }

    @Test
    void obtenerEventoTest(){
        Assertions.assertEquals(this.eventoPrueba,EventoService.obtenerEvento((long) 10) );
    }


    //TODO:
    @Test
    void obtenerEventoPorNombreTest(){
        Evento eventoObtenido = EventoService.obtenerEventoPorNombre("eventoPrueba");
        Assertions.assertEquals(this.eventoPrueba,eventoObtenido );
    }


    
    @Test
    void obtenerEventoPorNombreFailTest(){
        Assertions.assertThrows(EventNotFoundException.class,() ->
            EventoService.obtenerEventoPorNombre("eventoInexistente") );
    }

    //TODO: TEST PEDNIENTE
    @Test
    void validarModificacionTest(){
        when(userService.getRolUsuario(this.userPrueba)).thenReturn(this.userPrueba.getRol());

        when(userService.obtenerUsuarioPorNombre(this.userPrueba.getNombre())).thenReturn(this.userPrueba);

        Assertions.assertTrue(EventoService.validarModificacion(this.userPrueba, this.userPrueba.getToken()));
    }

    @Test
    void validarModificacionFailTest(){
        when(userService.getRolUsuario(this.userPrueba)).thenReturn(this.userPrueba.getRol());

        // when(userService.obtenerUsuarioPorNombre(this.userPrueba.getNombre())).thenReturn(this.userPrueba);

        Assertions.assertFalse(EventoService.validarModificacion(this.userPrueba, "tokenFalso"));
    }

    @Test
    void borrarEvento(){
        Map<String,String> response = EventoService.borrarEvento((long) 10);
        String status = response.get("status");
        Assertions.assertEquals("ok",status );
    
    }
    



    

    
}

