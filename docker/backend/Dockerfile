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
# heck if "recipes-images" exists in the build context
RUN echo "Checking if 'recipes-images' exists in the build context:" && ls -l recipes-images || echo "'recipes-images' does not exist!"

COPY --from=build /app/target/myKitchen-1.0-SNAPSHOT.jar app.jar
COPY --from=build /app/target/classes classes
COPY --from=build /app/target/dependency dependency
COPY csv-files /app/classes/data
COPY recipes-images/* /app/recipes-images
COPY /scripts/shell/wait-db.sh wait-db.sh
RUN chmod +x wait-db.sh
ENTRYPOINT ["./wait-db.sh"]