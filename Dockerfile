FROM maven:3.9.7-eclipse-temurin-22-alpine AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/app/app.jar"]