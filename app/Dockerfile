FROM openjdk:8-jdk-alpine

WORKDIR /var/www/html/

ARG JAR_FILE
ADD target/${JAR_FILE} app.jar

ENV JAVA_OPTS=""

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /var/www/html/app.jar" ]