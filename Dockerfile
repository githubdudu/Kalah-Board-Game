FROM eclipse-temurin:17-jdk AS build
WORKDIR /app
COPY Makefile .
COPY resources resources
COPY src src
COPY web web
RUN apt-get update && apt-get install -y --no-install-recommends make && rm -rf /var/lib/apt/lists/*
RUN make build-web

FROM caddy:2-alpine
COPY --from=build /app/web/dist /srv
HEALTHCHECK CMD wget -qO /dev/null http://localhost:${PORT:-8080}/ || exit 1
CMD caddy file-server --root /srv --listen :${PORT:-8080}
