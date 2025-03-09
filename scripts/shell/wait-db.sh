#!/bin/bash

echo "**********[BEFORE #1] my-kitchen-backend container waits until 'my-kitchen-database' container is fully ready **********"
until java -cp classes:dependency/* org.example.db.TestConnect; do
    echo "PostgreSQL is unavailable - sleeping 5 seconds"
    sleep 5
done
echo "**********[AFTER #1] TestConnect completed successfully! **********"

echo "**********[BEFORE #2] DataLoader keeps trying until it successfully creates db & load data into tables **********"
until java -cp classes:dependency/* org.example.db.DataLoader; do
    echo "DataLoader failed - retrying in 5 seconds"
    sleep 5
done
echo "**********[AFTER #2] DataLoader completed successfully! **********"

echo "**********[BEFORE #3] SqlFileExecutor keeps trying until all sql scripts executed successfully **********"
until java -cp classes:dependency/* org.example.db.SqlFileExecutor; do
    echo "SqlFileExecutor failed - retrying in 5 seconds"
    sleep 5
done
echo "**********[AFTER #3] SqlFileExecutor completed successfully! **********"

echo "********** Starting Spring Boot application **********"
exec java -jar app.jar

