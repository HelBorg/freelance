## alpine linux with JRE
FROM openjdk:8-jre-alpine

## copy the spring jar
COPY target/*.jar /opt/back-end-0.0.1.jar

CMD ["/usr/bin/java", "-jar", "/opt/back-end-0.0.1.jar"]