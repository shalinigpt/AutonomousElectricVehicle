FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY build/libs/test-0.0.1-SNAPSHOT.jar test.jar
ENTRYPOINT ["java","-jar","/test.jar"]