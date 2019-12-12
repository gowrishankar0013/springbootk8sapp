FROM openjdk:10.0.1-10-jre-slim
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/spring-boot-k8s-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} spring-boot-k8s.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/spring-boot-k8s.jar"]

