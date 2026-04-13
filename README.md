# 🏡 InmoHub - MVP Workspace

Bienvenido al repositorio principal de **InmoHub**, un sistema de gestión inmobiliaria (CRM) de modelo híbrido. Este proyecto se encuentra actualmente en fase de **Producto Mínimo Viable (MVP)**, desarrollado como parte de las entregas de mi proyecto final.

Este monorepo agrupa todo el ecosistema de la aplicación, permitiendo tener una visión global del sistema, desde la persistencia de datos y la lógica de negocio distribuida, hasta la interfaz de usuario multiplataforma.

## 🎯 Objetivo del Proyecto y Alcance del MVP

InmoHub busca modernizar la gestión inmobiliaria integrando a las agencias tradicionales con los propietarios particulares (FSBO - For Sale By Owner). 

En este MVP actual, el sistema implementa los flujos core de la aplicación:
* **Gestión de Identidad:** Autenticación de usuarios validando credenciales a través de un algoritmo de cifrado propio adaptado a esta fase del desarrollo.
* **Catálogo de Propiedades:** Creación, lectura y listado de inmuebles.
* **Arquitectura Distribuida:** Comunicación funcional entre el API Gateway, el registro de servicios y los microservicios de dominio.
* **Interfaz Multiplataforma:** Cliente funcional con navegación declarativa y consumo de APIs preparado para dispositivos móviles y de escritorio.

## 🏗️ Arquitectura y Tecnologías del MVP

El proyecto está dividido en dos grandes bloques independientes, cada uno con su propio stack tecnológico:

### Backend (Microservicios)
Desarrollado en **Java 21** y **Spring Boot 4**, el backend utiliza una arquitectura orientada a microservicios para aislar dominios funcionales:
* **Infraestructura Core:** API Gateway (punto de entrada único) y Eureka Server (descubrimiento de servicios).
* **Microservicios Activos:** `auth-service` (gestión de login y usuarios) y `property-service` (gestión del catálogo de inmuebles).
* **Persistencia:** Bases de datos relacionales independientes por servicio, gestionadas localmente.

### Frontend (KMP)
El cliente está construido con **Kotlin Multiplatform (KMP)** y **Compose Multiplatform**, permitiendo una única base de código para la interfaz y la lógica de negocio en diferentes dispositivos:
* **Targets implementados:** Android (Móvil) y Desktop (JVM).
* **Lógica Compartida:** Consumo de red mediante `Ktor Client` y navegación gestionada con `Voyager`.

## 📂 Estructura del Repositorio

La organización del código refleja la separación física de los componentes del sistema:

* `/backend` - Código fuente de la infraestructura Spring Boot y los microservicios.
* `/frontend` - Código fuente del proyecto Kotlin Multiplatform.
* `/docs` - Documentación funcional, análisis de mercado, diagramas de diseño (Secuencia, Flujo, Clases) y planificación.

## 🚀 Cómo probar el proyecto

Cada entorno tiene sus propios requisitos y comandos de ejecución. Por favor, consulta la documentación específica de cada módulo para levantarlos correctamente:

1. Para levantar los microservicios, bases de datos y el Gateway, dirígete a la [Guía del Backend](./inmohub-backend/README.md).
2. Para compilar y ejecutar la aplicación móvil o de escritorio, dirígete a la [Guía del Frontend](./inmohub-frontend/README.md).