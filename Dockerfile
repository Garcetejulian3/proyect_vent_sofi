# Imagen base con Java
FROM eclipse-temurin:17-jdk

# Carpeta interna del contenedor
WORKDIR /app

# Copiamos el jar generado
COPY target/proyect_vent_sofi-0.0.1-SNAPSHOT.jar app.jar

# Puerto que usará Render
EXPOSE 8080

# Comando de arranque
ENTRYPOINT ["java", "-jar", "app.jar"]