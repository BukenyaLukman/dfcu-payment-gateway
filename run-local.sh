#!/usr/bin/env bash
#
# scripts/run-local.sh
#

# Tear down any old container
docker rm -f dfcu-payment-gateway-local 2>/dev/null || true

# Run the Spring Boot app in Docker, pointing at your local Postgres
docker run \
  --name dfcu-payment-gateway-local \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/dfcu_payment_gateway \
  -e SPRING_DATASOURCE_USERNAME=bukenya \
  -e SPRING_DATASOURCE_PASSWORD=integration \
  -p 8080:8080 \
  dfcu-payment-gateway:latest 