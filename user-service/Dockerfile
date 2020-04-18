# For Java 8, try this

FROM openjdk:8-jdk-alpine

# Refer to Maven build -> finalName
ARG AUTH_JAR_FILE=target/*.jar

WORKDIR /user

COPY user-service/ .

RUN mkdir -p target

RUN chmod +x mvnw

RUN ./mvnw clean package

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"]