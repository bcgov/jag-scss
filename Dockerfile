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

COPY --from=build /target/scss-application.jar ./scss-application.jar

ENTRYPOINT ["java","-jar","/scss-application.jar"]

#############################################################################################