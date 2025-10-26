# Etapa 1: Build de la aplicación
FROM maven:3.9.9-eclipse-temurin-17 AS build

# Establecer directorio de trabajo
WORKDIR /app

# Copiar archivos de configuración de Maven
COPY pom.xml .

# Descargar dependencias (esto se cachea si pom.xml no cambia)
RUN mvn dependency:go-offline -B

# Copiar el código fuente
COPY src ./src

# Compilar la aplicación (sin ejecutar tests para acelerar)
RUN mvn clean package -DskipTests

# Etapa 2: Imagen de ejecución (más ligera)
FROM eclipse-temurin:17-jre-alpine

# Instalar tzdata para zonas horarias
RUN apk add --no-cache tzdata

# Establecer zona horaria
ENV TZ=America/Lima

# Crear usuario no-root para seguridad
RUN addgroup -S spring && adduser -S spring -G spring

# Crear directorio para uploads
RUN mkdir -p /app/uploads && chown -R spring:spring /app

# Cambiar a usuario no-root
USER spring:spring

# Establecer directorio de trabajo
WORKDIR /app

# Copiar el JAR desde la etapa de build
COPY --from=build --chown=spring:spring /app/target/*.jar app.jar

# Exponer el puerto
EXPOSE 8080

# Variables de entorno por defecto (pueden ser sobrescritas)
ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Comando de inicio con optimizaciones
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar"]
