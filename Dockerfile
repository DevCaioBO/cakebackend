# Etapa de build
FROM maven:3.9.9-amazoncorretto-17-alpine AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# Listar os arquivos no diretório target para diagnóstico
RUN ls -la /app/target

# Etapa de execução
FROM openjdk:17-jdk-slim

WORKDIR /app

# Ajustar para o nome correto do JAR gerado
COPY --from=build /app/target/cake-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]