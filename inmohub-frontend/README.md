# 📱 InmoHub - Cliente Multiplataforma (MVP)

Este proyecto contiene la interfaz de usuario y la lógica de cliente de **InmoHub**. Está desarrollado utilizando **Kotlin Multiplatform (KMP)** y **Compose Multiplatform**, lo que nos permite compartir la lógica de negocio, el consumo de red y el diseño visual entre varias plataformas usando una sola base de código en Kotlin.

Actualmente, este MVP cuenta con soporte funcional para dos entornos:
* 🟢 **Android** (Aplicación móvil optimizada para clientes/agentes en movimiento).
* 💻 **Desktop** (Aplicación de escritorio JVM optimizada para la gestión en oficina).

## 🛠️ Stack Tecnológico

* **Lenguaje:** Kotlin
* **UI Framework:** Compose Multiplatform
* **Navegación:** [Voyager](https://voyager.adriel.cafe/) (Navegación declarativa y gestión de pantallas).
* **Networking:** Ktor Client (Configurado con Content Negotiation para procesar respuestas JSON).
* **Arquitectura:** Patrón Repository para abstraer las llamadas a la API del backend y separar la lógica de presentación de los datos.

## ⚙️ Estructura del Proyecto

El código principal reside en el módulo compartido, garantizando la reutilización máxima:

* `composeApp/src/commonMain/kotlin/com/inmohub/frontend/`: Contiene toda la lógica agnóstica a la plataforma.
  * **Data:** Definición de modelos, configuración de Ktor y repositorios.
  * **UI:** Pantallas del MVP (Login, Listado de Propiedades) y componentes visuales reutilizables.
* `composeApp/src/androidMain/`: Punto de entrada y configuraciones específicas para Android.
* `composeApp/src/desktopMain/`: Punto de entrada y configuración de la ventana principal para JVM.

## 🚀 Requisitos Previos

* **JDK 21** o superior.
* **Android Studio** (Recomendado) o IntelliJ IDEA con el plugin de Kotlin Multiplatform.
* Emulador de Android o dispositivo físico (para el target móvil).
* El **Backend de InmoHub** debe estar en ejecución localmente para que el login y el listado de propiedades funcionen.

## 🚀 Ejecución del Proyecto

Sigue las instrucciones según la plataforma en la que quieras ejecutar la aplicación.

### Ejecutar en Desktop (JVM)

#### macOS / Linux:
```bash
./gradlew :composeApp:run
```

### Ejecutar en Android

Puedes usar la configuración de ejecución de Android Studio o compilar mediante la terminal:

**macOS / Linux:**
```bash
./gradlew :composeApp:assembleDebug
```

**Windows (PowerShell):**
```powershell
.\gradlew.bat :composeApp:assembleDebug
```

Una vez compilado, instala el APK generado en tu dispositivo o emulador.  