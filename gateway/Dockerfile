FROM adoptopenjdk/openjdk11:alpine-jre

# Refer to Maven build -> finalName
ARG JAR_FILE=target/*.jar

# cd /opt/app
WORKDIR /opt/app

# cp target/spring-boot-web.jar /opt/app/gateway.jar
COPY ${JAR_FILE} gateway.jar

# java -jar /opt/app/gateway.jar
ENTRYPOINT ["java","-jar","-Djava.security.egd=file:/dev/./urandom","gateway.jar"]