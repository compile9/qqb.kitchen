# Build Stage
FROM maven:3.9.9-eclipse-temurin-21-jammy AS build
WORKDIR /app
COPY pom.xml .
COPY src/main ./src/main
COPY recipes-images ./recipes-images
# check if "recipes-images" exists in the build stage
RUN echo "Checking if 'recipes-images' exists in the build stage:" && ls -l recipes-images || echo "'recipes-images' does not exist!"
RUN mvn clean package
RUN mvn dependency:copy-dependencies -DoutputDirectory=target/dependency


# Runtime Stage
FROM eclipse-temurin:21
WORKDIR /app
COPY --from=build /app/recipes-images recipes-images
# check if "recipes-images" exists in the runtime stage
RUN echo "Checking if 'recipes-images' exists in the runtime stage:" && ls -l recipes-images || echo "'recipes-images' does not exist!"
COPY --from=build /app/target/myKitchen-1.0-SNAPSHOT.jar app.jar
COPY --from=build /app/target/classes classes
COPY --from=build /app/target/dependency dependency
COPY csv-files /app/classes/data
# check CSV image column
RUN echo "=== Checking image paths in CSV files ===" && \
    for csv in /app/classes/data/*.csv; do \
      echo "File: $csv" && \
      echo "Image paths:" && \
      grep -o '"image","[^"]*"' "$csv" || \
      grep -o '"[^"]*","[^"]*","[^"]*","[^"]*","[^"]*"' "$csv" | cut -d',' -f5 || \
      awk -F, '{print $5}' "$csv" | grep -v "^$" | head -n 10; \
    done
COPY /scripts/shell/wait-db.sh wait-db.sh
RUN chmod +x wait-db.sh
ENTRYPOINT ["./wait-db.sh"]