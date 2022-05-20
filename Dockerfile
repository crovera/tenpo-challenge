FROM openjdk:18
COPY target/challenge-1.0.0.jar app.jar
ENTRYPOINT ["/usr/bin/java", "-jar", "/app.jar"]
EXPOSE 8080