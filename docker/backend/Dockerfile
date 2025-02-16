# Build Stage
FROM maven:3.9.9-eclipse-temurin-21-jammy AS build
WORKDIR /app
COPY pom.xml .
COPY src/main ./src/main
RUN mvn clean package
RUN mvn dependency:copy-dependencies -DoutputDirectory=target/dependency


# Runtime Stage
FROM eclipse-temurin:21
WORKDIR /app
COPY .env /app/.env
COPY --from=build /app/target/myKitchen-1.0-SNAPSHOT.jar app.jar
COPY --from=build /app/target/classes /app/classes
COPY --from=build /app/target/dependency /app/dependency
COPY /shell_scripts/wait-db.sh /app/wait-db.sh
RUN chmod +x /app/wait-db.sh
ENTRYPOINT ["/app/wait-db.sh"]