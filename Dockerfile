FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app
COPY . .
RUN apt-get update && apt-get install -y make && apt-get clean
RUN make build-web

FROM node:24-alpine
WORKDIR /app
COPY --from=builder /app/web/dist ./web/dist
COPY web/serve.js .
COPY web/package*.json .
RUN npm ci
EXPOSE 3000
CMD ["node", "serve.js"]

