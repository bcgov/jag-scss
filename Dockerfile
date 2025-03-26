FROM eclipse-temurin:17-jre-alpine

RUN apk update && apk add --upgrade --no-cache libexpat # fix CVE-2024-8176

COPY ./target/scss-application.jar scss-application.jar

ENTRYPOINT ["java","-jar","/scss-application.jar"]