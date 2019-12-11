FROM openjdk:8-jdk-alpine

COPY ./target/eureka-service-1.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-Dspring.profiles.active=deploy","-Xms256m","-Xmx512m","-jar","app.jar"]