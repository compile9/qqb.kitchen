#!/bin/bash

check_postgres() {
	java -cp classes:dependency/* org.example.db.TestConnect
	return $?
}

echo "Waiting for PostgreSQL to be ready..."
until check_postgres; do
	echo "PostgreSQL is unavailable - sleeping 5 seconds"
	sleep 5
done

echo "PostgreSQL connection established - waiting 5 more seconds for full initialization..."
sleep 5

echo "PostgreSQL is up - executing database setup"

if ! java -cp classes:dependency/* org.example.db.DataLoader; then
	echo "Failed to run DataLoader"
	exit 1
fi

if ! java -cp classes:dependency/* org.example.db.SqlFileExecutor; then
	echo "Failed to run SqlFileExecutor"
	exit 1
fi

echo "Starting main application..."
exec java -jar app.jar

