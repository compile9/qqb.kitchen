# Build Stage
FROM maven:3.9.9-eclipse-temurin-21-jammy AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package
RUN mvn dependency:copy-dependencies -DoutputDirectory=target/dependency


# Runtime Stage
FROM eclipse-temurin:21
WORKDIR /app
COPY .env /app/.env
COPY --from=build /app/target/myKitchen-1.0-SNAPSHOT.jar app.jar
COPY --from=build /app/target/classes /app/classes
COPY --from=build /app/target/dependency /app/dependency
RUN java -cp classes:dependency/* org.example.db.TestConnect
RUN java -cp classes:dependency/* org.example.db.DataLoader
RUN java -cp classes:dependency/* org.example.db.SqlFileExecutor
ENTRYPOINT ["java","-jar","app.jar"]