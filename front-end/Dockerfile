##RUN mvn clean install
## alpine linux with JRE
FROM openjdk:8-jre-alpine

## copy the spring jar
COPY target/front-end-0.0.1.jar front-end-0.0.1.jar

CMD ["/usr/bin/java", "-jar", "front-end-0.0.1.jar"]
