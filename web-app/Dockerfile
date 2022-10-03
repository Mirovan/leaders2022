FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine
FROM maven:3.6.3-jdk-11-openj9

WORKDIR /app
COPY web-app/pom.xml /app
COPY web-app/backend-core /app/backend-core
COPY web-app/backend-web /app/backend-web
COPY web-app/migration /app/migration

RUN mvn clean package

WORKDIR /app/backend-web/target
CMD ["java", "-jar", "/app/target/web-0.0.1-SNAPSHOT.jar"]