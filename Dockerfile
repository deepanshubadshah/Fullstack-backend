FROM openjdk:17-slim
ARG JAR_FILE=target/*.jar
COPY ./target/FullstackApp-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]