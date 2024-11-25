FROM openjdk:17
COPY target/javaparcial-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 7080
ENTRYPOINT [ "java","-jar", "app.jar" ]