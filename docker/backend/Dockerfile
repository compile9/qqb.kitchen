# Build Stage
FROM maven:3.9.8-openjdk-21-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

# Runtime Stage
FROM openjdk:21-jdk-alpine
WORKDIR /app
ARG PORT
ENV PORT=${PORT}
COPY --from=build /app/target/myKitchen-1.0-SNAPSHOT.jar app.jar
EXPOSE ${PORT}
ENTRYPOINT ["java","-jar","app.jar"]