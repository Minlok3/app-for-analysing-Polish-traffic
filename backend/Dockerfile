# Dockerfile

# FROM openjdk:17

FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY target/ /app

COPY classes/custom/application.properties /app/custom/application.properties

COPY ./wait-for-it.sh /app/wait-for-it.sh

RUN chmod +x /app/wait-for-it.sh

EXPOSE 8080

ENTRYPOINT ["./wait-for-it.sh", "database:3306", "--", "java", "-jar", "backend-0.0.1-SNAPSHOT.jar", "--spring.config.location=classpath:/application.properties,file:/app/custom/application.properties"]

