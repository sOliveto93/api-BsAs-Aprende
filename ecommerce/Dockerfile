#FROM openjdk:21-jdk-slim
#ARG JAR_FILE=target/ecommerce-0.0.1.jar
#COPY ${JAR_FILE} app_ecommerce.jar
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","app_ecommerce.jar"]

# Etapa 1: Build con Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Imagen ligera con OpenJDK
FROM openjdk:21-jdk-slim
COPY --from=build /app/target/ecommerce-0.0.1.jar app_ecommerce.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app_ecommerce.jar"]
