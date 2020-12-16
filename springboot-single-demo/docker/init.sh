#!/bin/bash

if [ -z "$SERVER_PORT" ]; then
    SERVER_PORT=8080
fi
if [ -z "$DB_HOST" ]; then
    DB_HOST="demo.aws.com"
fi
if [ -z "$DB_PORT" ]; then
    DB_PORT=5432
fi
if [ -z "$DB_USER" ]; then
    DB_USER="evangtech"
fi

java -jar demo-1.0.jar -Dserver.port=${SERVER_PORT} DB_HOST=${DB_HOST} DB_PORT=${DB_PORT} DB_USER=${DB_USER}
