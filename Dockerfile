FROM eclipse-temurin:11-jre-jammy

COPY ./target/scss-application.jar scss-application.jar

ENTRYPOINT ["java","-jar","/scss-application.jar"]