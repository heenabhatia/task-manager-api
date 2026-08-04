#!/bin/bash

set -e

docker compose down

./mvnw clean package || exit 1

docker build -t task-manager-api . || exit 1

docker compose up -d