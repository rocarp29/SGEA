package com.templates.apirest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.templates.apirest.configuracion.Roles;
import com.templates.apirest.model.Comentario;
import com.templates.apirest.model.Evento;
import com.templates.apirest.model.Presentacion;
import com.templates.apirest.model.Usuario;
import com.templates.apirest.service.impl.ComentarioService;
import com.templates.apirest.service.impl.EventosService;
import com.templates.apirest.service.impl.PresentacionService;
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
			(UserService userService, EventosService eventoService, 
			ComentarioService comentarioService, PresentacionService presentacionService){
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

			Evento evento1 = new Evento("EventoPrueba",LocalDateTime.now());
			Evento eventoCom1 = new Evento("EventoPruebaComentarios",LocalDateTime.parse("2021-03-12 12:30",Evento.FORMATO_FECHA));
			Evento eventoPres1 = new Evento("EventoPruebaPresentaciones",LocalDateTime.parse("2021-03-12 12:30",Evento.FORMATO_FECHA));
			Evento eventoIntegral = new Evento("EventoIntegral",LocalDateTime.parse("2021-03-12 12:30",Evento.FORMATO_FECHA));

			eventoService.guardarEvento(evento1);
			eventoService.guardarEvento(eventoCom1);
			eventoService.guardarEvento(eventoPres1);
			eventoService.guardarEvento(eventoIntegral);
			
			
			/**
			 * Comentarios de prueba
			 */
			Comentario comment1 = new Comentario(LocalDate.now().plusDays(1),"Comentario de prueba");
			Comentario comment2 = new Comentario(LocalDate.now().plusDays(2),"Comentario dos de prueba");
			Comentario comment3 = new Comentario(LocalDate.now().plusDays(2),"Comentario tres de prueba");
			//Solo funca si mandas evento 
			Comentario comment1A = new Comentario(LocalDate.now().plusDays(1),"Comentario asginado de prueba",eventoCom1, admin1);
			Comentario comment2A = new Comentario(LocalDate.now().plusDays(2),"Comentario asginadod os de prueba",eventoCom1, admin1);
			Comentario comment3A = new Comentario(LocalDate.now().plusDays(2),"Comentario asginado tres de prueba",eventoCom1, admin2);
			comentarioService.crearComentario(comment1);
			comentarioService.crearComentario(comment2);
			comentarioService.crearComentario(comment3);

			comentarioService.crearComentario(comment1A);
			comentarioService.crearComentario(comment2A);
			comentarioService.crearComentario(comment3A);
			/**
			 * Presentaciones de prueba
			 */
			Presentacion presentacion3 = new Presentacion(LocalDateTime.parse("2007-12-03T10:15:30"),"TituloPresPrueba","Organiz Prueba",eventoPres1);
			Presentacion presentacion4 = new Presentacion(LocalDateTime.parse("2007-12-03T10:15:30"),"TituloPresPrueba2","Organiz Prueba2",eventoPres1);
			
			
			userService.actualizarUsuario(admin1);
			userService.actualizarUsuario(admin2);
			presentacionService.crearPresentacion(presentacion3);
			presentacionService.crearPresentacion(presentacion4);
			presentacionService.addAsistente(presentacion3, admin1);
			presentacionService.addAsistente(presentacion3, admin2);
			



			/**
			 * Creo evento de prueba.
			 */

			eventoCom1.addComentario(comment1);
			eventoCom1.addComentario(comment2);

			System.out.println("@@@" + eventoPres1.getPresentaciones().size());
			// eventoIntegral.addComentario(comment1);
			// eventoIntegral.addComentario(comment2);
			


			eventoService.guardarEvento(evento1);
			eventoService.guardarEvento(eventoCom1);
			eventoService.guardarEvento(eventoPres1);
			eventoService.guardarEvento(eventoIntegral);
			
			



		};
	}

}
