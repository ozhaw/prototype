FROM openjdk:8-jdk-alpine

COPY ./target/device-service-1.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-Dspring.profiles.active=deploy,heroku","-Xms256m", "-Xmx512m", "-jar","app.jar"]