# Build Stage
FROM maven:3.9.9-eclipse-temurin-21-jammy AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

# Runtime Stage
FROM eclipse-temurin:21
WORKDIR /app
ARG PORT
ENV PORT=${PORT}
COPY --from=build /app/target/myKitchen-1.0-SNAPSHOT.jar app.jar
EXPOSE ${PORT}
ENTRYPOINT ["java","-jar","app.jar"]