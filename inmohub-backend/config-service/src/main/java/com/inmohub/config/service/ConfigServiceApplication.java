package com.inmohub.config.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Clase principal del microservicio Config Service.
 *
 * Este servicio actúa como un servidor de configuración centralizada para el sistema distribuido.
 *
 * Funcionamiento:
 * Los demás microservicios (auth, property, gateway) contactan a este servicio al arrancar
 * (puerto 8888) para solicitar sus credenciales, URLs de base de datos y configuraciones.
 * Esto permite cambiar la configuración sin necesidad de recompilar los microservicios clientes.
 *
 * @author Joaquin Gabriel Puchuri Tunjar
 * @version 1.0
 */
@SpringBootApplication
@EnableConfigServer // Habilita la funcionalidad de servidor de configuración de Spring Cloud
public class ConfigServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServiceApplication.class, args);
    }

}
