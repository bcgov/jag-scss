FROM eclipse-temurin:17-jre-alpine

RUN apk upgrade libssl3 libcrypto3 # Fix for CVE-2023-2650

COPY ./target/scss-application.jar scss-application.jar

ENTRYPOINT ["java","-jar","/scss-application.jar"]