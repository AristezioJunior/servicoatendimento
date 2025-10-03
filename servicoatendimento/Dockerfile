FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

ARG JAR_FILE

COPY ${JAR_FILE} api.jar
COPY wait-for-it.sh /wait-for-it.sh

RUN chmod +x /wait-for-it.sh

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "api.jar"]