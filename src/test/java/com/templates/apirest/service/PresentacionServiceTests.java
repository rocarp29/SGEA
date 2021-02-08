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
import com.templates.apirest.configuracion.exceptions.PresentationNotFoundException;
import com.templates.apirest.model.Presentacion;
import com.templates.apirest.model.Evento;
import com.templates.apirest.model.Usuario;

import com.templates.apirest.persistence.impl.PresentacionRepository;
import com.templates.apirest.persistence.impl.PresentacionRepository;
import com.templates.apirest.service.impl.PresentacionService;
import com.templates.apirest.service.impl.EventoService;
import com.templates.apirest.service.impl.UserService;
import com.templates.apirest.service.impl.PresentacionService;

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
public class PresentacionServiceTests extends AbstractTestNGSpringContextTests{
    @InjectMocks                           
    private PresentacionService presentacionService;

    @Mock                         
    private PresentacionRepository presentacionRepository;
    @Mock                         
    private UserService userService;
    @Mock
    private EventoService eventoService;
    

    @Mock
    private ModelMapper modelMapper;


    private ObjectMapper om;

    private Presentacion presentacionPrueba;
    private Optional<Presentacion> optionalPresentacionPrueba;
    private Presentacion presentacionPrueba2;
    private List<Presentacion> presentacionListaPrueba;
    private LocalDateTime fechaActual;
    private Usuario userPrueba;
    private Evento eventoPrueba;

    @BeforeEach
     public void setUp() {
         this.fechaActual = LocalDateTime.now();
        
        this.om = new ObjectMapper();
        MockitoAnnotations.openMocks(this); // this is needed for inititalizytion of mocks, if you use @Mock 
        this.userPrueba = new Usuario("testUsuario","testUsuario",Roles.ADMIN,"token");
        this.eventoPrueba = new Evento("eventoPrueba",LocalDateTime.now());
        this.eventoPrueba.setId((long)15);

        this.presentacionPrueba = new Presentacion(fechaActual,"presentacionPrueba", "Sociedad de prueba", this.eventoPrueba);
        this.presentacionPrueba2 = new Presentacion(fechaActual,"presentacionPrueba2","Sociedad de prueba", this.eventoPrueba);
        this.eventoPrueba.addPresentacion(presentacionPrueba);
        this.eventoPrueba.addPresentacion(presentacionPrueba2);
        this.presentacionService = new PresentacionService(presentacionRepository, userService);
        

        this.presentacionListaPrueba = new ArrayList<Presentacion>();
        this.presentacionListaPrueba.add(presentacionPrueba);
        this.presentacionListaPrueba.add(presentacionPrueba2);

        
        presentacionPrueba.setId((long)10);
        when(presentacionRepository.save(this.presentacionPrueba)).thenReturn(presentacionPrueba);
        when(presentacionRepository.getOne((long) 10)).thenReturn(presentacionPrueba);
        when(presentacionRepository.getOne((long) 11)).thenReturn(presentacionPrueba2);
        when(presentacionRepository.findAll()).thenReturn(presentacionListaPrueba);
        when(presentacionRepository.findById((long)10)).thenReturn(Optional.of(presentacionPrueba));
        when(presentacionService.obtenerPresentaciones().stream().filter(
            presentacion -> this.eventoPrueba.getId().equals(presentacion.getId()))
            .collect(Collectors.toList()))
            .thenReturn(this.presentacionListaPrueba);
        

    }

    @Test
    void guardadoPresentacionTest(){
        Presentacion presentacionGuardado =  presentacionService.crearPresentacion(this.presentacionPrueba);
        
        Assertions.assertEquals(this.presentacionPrueba,presentacionGuardado );
        
    }

    
    @Test
    void obtenerPresentacionsTest(){
        List<Presentacion> presentacionsGuardado =  presentacionService.obtenerPresentaciones();
        Assertions.assertEquals(this.presentacionListaPrueba, presentacionsGuardado);        
    }

    //TODO:
    @Test
    void obtenerPresentacionsPorEventoTest(){
        List<Presentacion> presentacionsEvento =  presentacionService.obtenerPresentacionsPorEvento(this.eventoPrueba);
        Assertions.assertEquals(this.presentacionListaPrueba, presentacionsEvento);        
    }
    
    @Test
    
    void obtenerPresentacionsPorEventoFailTest(){
        Evento eventoError = new Evento("eventoPruebaError",LocalDateTime.now());
        Assertions.assertThrows(PresentationNotFoundException.class,() -> presentacionService.obtenerPresentacionsPorEvento(eventoError));        
    }

    
    @Test
    void borrarPresentacionPorIdTest(){
        Map<String,String> responseService = presentacionService.borrarPresentacionPorId((long)10);
        Assertions.assertEquals("ok", responseService.get("status"));        
    }
        

}



