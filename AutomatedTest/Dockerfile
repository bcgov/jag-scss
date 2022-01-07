FROM openjdk:11-jre-slim

COPY ./target/scss-test-runner.jar scss-test-runner.jar

ENTRYPOINT ["java","-Xss2048k","-jar","/scss-test-runner.jar"]
