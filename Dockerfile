FROM adoptopenjdk/openjdk11:jdk-11.0.10_9-alpine
EXPOSE 8080
ARG JAR_FILE=target/newsapp*.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java","-jar","/application.jar"]