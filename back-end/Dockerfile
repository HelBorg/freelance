##RUN mvn clean install
## alpine linux with JRE
FROM openjdk:8-jre-alpine

## copy the spring jar
COPY target/back-end-0.0.1.jar back-end-0.0.1.jar

CMD ["/usr/bin/java", "-jar", "back-end-0.0.1.jar"]