# For Java 8, try this

FROM openjdk:8-jdk-alpine

# Refer to Maven build -> finalName
ARG USER_JAR_FILE=target/*.jar

WORKDIR /user

COPY user-service/ .

RUN mkdir -p target

RUN chmod +x mvnw

RUN ./mvnw clean package

RUN cp ${USER_JAR_FILE} /app.jar

ENTRYPOINT ["java","-jar","/app.jar"]