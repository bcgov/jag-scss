# SCSS

[![Lifecycle:Stable](https://img.shields.io/badge/Lifecycle-Stable-97ca00)](https://github.com/bcgov/jag-scss)
[![Maintainability](https://api.codeclimate.com/v1/badges/5a7027d5cc5800eeb2fe/maintainability)](https://codeclimate.com/github/bcgov/jag-coa-scss-integration/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/5a7027d5cc5800eeb2fe/test_coverage)](https://codeclimate.com/github/bcgov/jag-coa-scss-integration/test_coverage)
### Recommended Tools
* Intellij
* Docker
* Maven
* Java 11

### Application Endpoints

Local Host: http://127.0.0.1:8080

WSDL Endpoint Local: http://localhost:8080/ws/SCSS.Source.CeisScss.ws.provider:CeisScss?WSDL

Actuator Endpoint Local: http://localhost:8080/actuator/health

### Required Environmental Variables

BASIC_AUTH_PASS: The password for the basic authentication. This can be any value for local.

BASIC_AUTH_USER: The username for the basic authentication. This can be any value for local.

ORDS_HOST: The url for ords rest package.

SPLUNK_HTTP_URL: The url for the spluck hec. For local splunk this value should be 127.0.0.1:8088 for
remote do not include /services/collector.

SPLUNK_TOKEN: The bearer token to authenticate the application.

SPLUNK_INDEX: The index that the application will push logs to. The index must be created in splunk
before they can be pushed to.

### Building the Application
1) Make sure using java 11 for the project modals and sdk
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

### Pre Commit
1) Do not commit \CRLF use unix line enders
2) Run the linter ```mvn spotless:apply```

### JaCoCo Coverage Report
1) Run ```mvn test```
2) Run ```mvn jacoco:report```
3) Open ```target/site/jacoco/index.html``` in a browser
