#FROM openjdk:21-jdk-slim
#ARG JAR_FILE=target/ecommerce-0.0.1.jar
#COPY ${JAR_FILE} app_ecommerce.jar
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","app_ecommerce.jar"]

########################################
#Si queres despliegue local descomenta la config de arriba
#######################################

# Etapa 1: Construcción
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .

# Ir al subdirectorio ecommerce donde está el pom.xml
WORKDIR /app/ecommerce
RUN mvn clean package -DskipTests

# Etapa 2: Imagen ligera
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/ecommerce/target/*.jar app_ecommerce.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app_ecommerce.jar"]

