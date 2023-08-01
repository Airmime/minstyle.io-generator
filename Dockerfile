FROM maven:3.6.3-openjdk-11-slim AS builder
COPY pom.xml /app/
COPY src /app/src/
WORKDIR /app
COPY pom.xml .
COPY src/ /app/src/
RUN mvn package -DskipTests

# Use AdoptOpenJDK for base image.
FROM adoptopenjdk:11-jre-hotspot
COPY --from=builder /app/target/*.jar /app.jar
WORKDIR /app
EXPOSE 42000
ENTRYPOINT ["java", "-jar", "/app.jar"]