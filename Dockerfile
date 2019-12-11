FROM maven:3.5.3-jdk-10-slim as build

WORKDIR /app
COPY pom.xml .
COPY src src

FROM openjdk:10.0.1-10-jre-slim

EXPOSE 8080
ADD target/*.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
