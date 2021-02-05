package com.templates.apirest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.templates.apirest.configuracion.Roles;
import com.templates.apirest.model.Comentario;
import com.templates.apirest.model.Evento;
import com.templates.apirest.model.Presentacion;
import com.templates.apirest.model.Usuario;
import com.templates.apirest.persistence.impl.EventoRepository;
import com.templates.apirest.service.impl.EventosService;
import com.templates.apirest.service.impl.UserService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.templates.apirest.*"})
public class ApirestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApirestApplication.class, args);
	}

	//Datos basicos de aplicacion.
	@Bean
	public CommandLineRunner initData2
			(UserService userService, EventosService eventoService){
		return args -> {
			
			
			String TOKEN_DEFAULT = "token";
			Usuario admin1 = new Usuario("admin", "admin", Roles.ADMIN, TOKEN_DEFAULT);
			Usuario admin2 = new Usuario("admin2", "admin2", Roles.ADMIN,TOKEN_DEFAULT);
			Usuario user1 = new Usuario("usuario", "usuario", Roles.USUARIO,TOKEN_DEFAULT);
			Usuario visit1 = new Usuario("visitante", "visitante", Roles.VISITANTE,TOKEN_DEFAULT);

			userService.crearUsuario(admin1);
			userService.crearUsuario(admin2);
			userService.crearUsuario(user1);
			userService.crearUsuario(visit1);


			/**
			 * Comentarios de prueba
			 */
			Comentario comment1 = new Comentario(LocalDate.now().plusDays(1),"Comentario de prueba");
			Comentario comment2 = new Comentario(LocalDate.now().plusDays(2),"Comentario dos de prueba");
			Comentario comment3 = new Comentario(LocalDate.now().plusDays(2),"Comentario tres de prueba");
			
			/**
			 * Presentaciones de prueba
			 */
			Presentacion presentacion = new Presentacion(LocalDateTime.parse("2007-12-03T10:15:30"),"TituloPresPrueba","Organiz Prueba");
			Presentacion presentacion2 = new Presentacion(LocalDateTime.parse("2007-12-03T10:15:30"),"TituloPresPrueba2","Organiz Prueba2");
			

			/**
			 * Creo evento de prueba.
			 */

			Evento evento1 = new Evento("EventoPrueba",LocalDateTime.now());
			
			Evento eventoCom1 = new Evento("EventoPruebaComentarios",LocalDateTime.parse("2021-03-12 12:30",Evento.FORMATO_FECHA));
			eventoCom1.setComentarios(comment1);
			eventoCom1.setComentarios(comment2);

			System.out.println("@@@@ " +eventoCom1.getComentarios());

			Evento eventoPres1 = new Evento("EventoPruebaPresentaciones",LocalDateTime.parse("2021-03-12 12:30",Evento.FORMATO_FECHA));
			eventoPres1.setPresentaciones(presentacion);
			eventoPres1.setPresentaciones(presentacion2);

			Evento eventoIntegral = new Evento("EventoIntegral",LocalDateTime.parse("2021-03-12 12:30",Evento.FORMATO_FECHA));
			eventoIntegral.setComentarios(comment1);
			eventoIntegral.setComentarios(comment2);
			eventoIntegral.setPresentaciones(presentacion);
			eventoIntegral.setPresentaciones(presentacion2);


			eventoService.guardarEvento(evento1);
			eventoService.guardarEvento(eventoCom1);
			eventoService.guardarEvento(eventoPres1);
			eventoService.guardarEvento(eventoIntegral);
			
			



		};
	}

}
