# uma maneira mais otimizada, porém mais complexa,
# de fazer a build pode ser encontrada aqui:
# https://stackoverflow.com/a/60546900/22667557

FROM maven:3.9.8-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
# salva dependencias baixadas num cache para reuso
RUN --mount=type=cache,target=/root/.m2 mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-jammy
ARG JAR_FILE=/app/target/*.jar
WORKDIR /app
COPY --from=builder $JAR_FILE /app/runner.jar
EXPOSE 8080
ENTRYPOINT java -jar /app/runner.jar
