package com.inmohub.api.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal del API Gateway.
 *
 * Este microservicio actúa como el punto de entrada único para toda la arquitectura.
 * Implementa el patrón "API Gateway" utilizando Spring Cloud Gateway sobre un stack reactivo (WebFlux/Netty).
 *
 *
 * Responsabilidades principales:
 * <ul>
 * <li>Enrutamiento dinámico: Redirige las peticiones a {@code auth-service} o {@code property-service}.</li>
 * <li>Balanceo de carga: Utiliza Eureka y Spring Cloud LoadBalancer para distribuir tráfico.</li>
 * <li>Seguridad perimetral: Punto central para validación de cabeceras (en futuras fases).</li>
 * </ul>
 *
 *
	 * @author Joaquin GAbriel Puchuri Tunjar
 * @version 1.0
 */
@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}
