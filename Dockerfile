FROM eclipse-temurin:11-jre-alpine

COPY ./target/scss-application.jar scss-application.jar

ENTRYPOINT ["java","-jar","/scss-application.jar"]