# 1.Construcción
# Usamos una imagen con Maven y JDK completo
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos el pom padre y descargamos dependencias
COPY pom.xml .
RUN mvn clean install -N

# Copiamos el código fuente y el pom específico del servicio
COPY service-registry/pom.xml ./service-registry/pom.xml
COPY service-registry/src ./service-registry/src

# Empaquetamos el jar saltando los tests para agilizar la construcción en Docker
RUN mvn -f service-registry/pom.xml clean package -DskipTests

# 2. Ejecución
# Usamos una imagen JRE ligera
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiamos solo el artefacto compilado (JAR) de la etapa anterior
COPY --from=build /app/service-registry/target/*.jar app.jar

# Exponemos el puerto estándar de Eureka
EXPOSE 8761

# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]