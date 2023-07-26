FROM maven:3.6.3-openjdk-11-slim AS builder
WORKDIR /app
COPY pom.xml .
COPY src/ /app/src/
RUN mvn package

# Use AdoptOpenJDK for base image.
FROM adoptopenjdk:11-jre-hotspot
COPY --from=builder /app/target/*.jar /app.jar
EXPOSE 42000
ENTRYPOINT ["java", "-jar", "/app.jar"]