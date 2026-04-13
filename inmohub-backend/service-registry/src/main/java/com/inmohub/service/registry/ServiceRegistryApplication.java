package com.inmohub.service.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Clase principal del microservicio Service Registry (Eureka Server).
 *
 * Este componente actúa como el "listín telefónico" de la arquitectura de microservicios.
 * Su responsabilidad es mantener un registro en tiempo real de todas las instancias
 * de servicios disponibles (auth-service, property-service, gateway, etc.).
 *
 * @author Joaquin Gabriel Puchuri Tunjar
 * @version 1.0
 */
@SpringBootApplication
@EnableEurekaServer // Habilita la funcionalidad de servidor de registro de Netflix Eureka
public class ServiceRegistryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceRegistryApplication.class, args);
    }

}
