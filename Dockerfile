FROM openjdk:8-jdk-alpine
EXPOSE 8080
ADD target/integrationdemo.jar integrationdemo.jar
ENTRYPOINT exec java $JAVA_OPTS -jar integrationdemo.jar