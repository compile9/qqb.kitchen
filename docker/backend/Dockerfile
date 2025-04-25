# Build Stage
FROM maven:3.9.9-eclipse-temurin-21-jammy AS build
WORKDIR /app
COPY pom.xml .
COPY src/main ./src/main
COPY recipes-images ./recipes-images
RUN mvn clean package
RUN mvn dependency:copy-dependencies -DoutputDirectory=target/dependency


# Runtime Stage
FROM eclipse-temurin:21
WORKDIR /app
COPY --from=build /app/recipes-images recipes-images
COPY --from=build /app/target/qqb.kitchen-1.0-SNAPSHOT.jar app.jar
COPY --from=build /app/target/classes classes
COPY --from=build /app/target/dependency dependency
COPY csv-files /app/classes/data
COPY /scripts/shell/wait-db.sh wait-db.sh
RUN chmod +x wait-db.sh
ENTRYPOINT ["./wait-db.sh"]