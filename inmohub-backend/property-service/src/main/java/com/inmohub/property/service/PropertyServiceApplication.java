package com.inmohub.property.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Punto de entrada del Microservicio de Propiedades.
 *
 * Gestiona el ciclo de vida de los inmuebles (creación, listado, borrado).
 *
 * Configuraciones habilitadas:
 * <ul>
 * <li>{@link EnableJpaAuditing}: Permite el autocompletado de fechas {@code createdAt} y {@code updatedAt}.</li>
 * <li>{@link EnableFeignClients}: Habilita el cliente HTTP declarativo (OpenFeign) para comunicarse
 * síncronamente con el microservicio {@code auth-service} y validar propietarios.</li>
 * </ul>
 */
@SpringBootApplication
@EnableJpaAuditing // Para que @CreatedDate y @LastModifiedDate funcionen y rellenen las fechas automaticamente
@EnableFeignClients // Habilitamos los clientes Feing
public class PropertyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PropertyServiceApplication.class, args);
	}

}
