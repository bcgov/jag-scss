# SCSS

[![Lifecycle:Stable](https://img.shields.io/badge/Lifecycle-Stable-97ca00)](https://github.com/bcgov/jag-scss)
[![Build & Vulnerability Scan using Trivy Scanner](https://github.com/bcgov/jag-scss/actions/workflows/trivy-analysis.yml/badge.svg)](https://github.com/bcgov/jag-scss/actions/workflows/trivy-analysis.yml)
[![Main - Build Image and Push to Openshift Registry for Dev Deployment](https://github.com/bcgov/jag-scss/actions/workflows/main.yml/badge.svg)](https://github.com/bcgov/jag-scss/actions/workflows/main.yml)

### Recommended Tools
* Intellij
* Docker
* Maven
* Java 17

### Application Endpoints

Local Host: http://127.0.0.1:8080

WSDL Endpoint Local: http://localhost:8080/ws/SCSS.Source.CeisScss.ws.provider:CeisScss?WSDL

Actuator Endpoint Local: http://localhost:8080/actuator/health

### Required Environmental Variables

BASIC_AUTH_PASS: The password for the basic authentication. This can be any value for local.

BASIC_AUTH_USER: The username for the basic authentication. This can be any value for local.

ORDS_HOST: The url for requesting ords and rest package.
ORDS_USERNAME: ORDS_HOST authentication
ORDS_PASSWORD: ORDS_HOST authentication
ORDS_READ_TIMEOUT: Timeout in seconds which expects the response/result from ORDS.

### Optional Enviromental Variables
SPLUNK_HTTP_URL: The url for the splunk hec.

SPLUNK_TOKEN: The bearer token to authenticate the application.

SPLUNK_INDEX: The index that the application will push logs to. The index must be created in splunk
before they can be pushed to.

### Building the Application
1) Make sure using java 17 for the project module and sdk
2) Run ``mvn compile``
3) Make sure ```target/generated-sources/xjc``` folder in included in module path

### Running the application
Via IDE
1) Set env variables.
2) Run the application

Via Jar
1) Run ```mvn package```
2) Run ```java -jar ./target/scss-application.jar```

Via Docker
1) Run ```mvn package```
2) Run ```docker build -t scss-api .``` from root folder
3) Run ```docker run -p 8080:8080 scss-api```

### JaCoCo Coverage Report
1) Run ```mvn test```
2) Run ```mvn jacoco:report```
3) Open ```target/site/jacoco/index.html``` in a browser
