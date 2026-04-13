package com.inmohub.auth.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Clase principal de arranque para el microservicio de Autenticación (Auth-Service).
 *
 * Esta clase contiene el método {@code main} que inicializa el contexto de Spring Boot.
 * Es responsable de levantar el servidor embebido (Tomcat) y configurar el escaneo de componentes.
 *
 * Configuraciones clave:
 * <ul>
 * <li>{@link EnableJpaAuditing}: Habilita el mecanismo de auditoría de Spring Data JPA.
 * Esto permite que los campos anotados con {@code @CreatedDate} y {@code @LastModifiedDate}
 * en las entidades (como en la clase {@code User}) se rellenen automáticamente con la fecha
 * y hora del sistema al realizar inserciones o actualizaciones.</li>
 * </ul>
 *
 * @author Joaquin Gabriel Puchuri Tunjar
 * @version 1.0
 */
@SpringBootApplication
@EnableJpaAuditing // Para que @CreatedDate y @LastModifiedDate funcionen y rellenen las fechas automaticamente
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

}
