FROM openjdk:11-jdk-slim

WORKDIR /app

COPY ./MyBookApp_java.jar /app/MyBookApp_java.jar

CMD ["java", "-jar", "/app/MyBookApp_java.jar"]