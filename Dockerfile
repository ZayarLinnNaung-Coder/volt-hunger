FROM openjdk:17-jdk-slim as build

#Information around who maintains the image
MAINTAINER ZAYARLINNNAUNG

# Add the application's jar to the container
COPY target/volt-hunger-0.0.1-SNAPSHOT.jar volt-hunger.jar

EXPOSE 8080

#execute the application
ENTRYPOINT ["java","-jar","/volt-hunger.jar"]
