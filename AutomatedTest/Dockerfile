#############################################################################################
###              Stage where Docker is building spring boot app using maven               ###
#############################################################################################
FROM maven:3.8.3-openjdk-17 AS build

WORKDIR /

COPY . .

RUN mvn clean package


#############################################################################################
### Stage where Docker is running a java process to run a service built in previous stage ###
#############################################################################################
FROM eclipse-temurin:17-jre-alpine

#CVE Fixes
RUN apk -U upgrade
RUN apk update && apk add --upgrade --no-cache sqlite # CVE fixes

RUN apk update && apk add --upgrade --no-cache libexpat # fix CVE-2024-8176

WORKDIR /app
RUN chgrp -R 0 /app && \
    chmod -R g=u /app

COPY --from=build ./target/scss-test-runner.jar ./scss-test-runner.jar

ENTRYPOINT ["java","-Xss2048k","-jar","scss-test-runner.jar"]

#############################################################################################