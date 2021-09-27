## SCSS

[![Lifecycle:Experimental](https://img.shields.io/badge/Lifecycle-Experimental-339999)](<Redirect-URL>)

### Recommended Tools
* Intellij
* Docker
* Docker Compose
* Maven
* Java 11

### Building the Application
1) Set intellij to use java 11 for the project modals and sdk
2) Run ``mvn compile``
3) Make sure ```target/generated-sources/xjc``` folder in included in module path

### Running the application
Option A) Intellij
1) Create intellij run configuration from SCSS Application
2) Set env variables. See the .env-template
3) Run the application

Option B) Jar
1) Run ```mvn package```
2) Run ```java -jar ./target/scss-application.jar```

Option C) Docker
1) Run ```mvn package```
2) Run ```docker build -t scss-api .``` from root folder
3) Run ```docker run -p 8080:8080 scss-api```

Option D) Docker Compose
1) Run ```mvn package```
2) Run ```docker-compose up scss-api```

### Pre Commit
1) Do not commit \CRLF use unix line enders
2) Run the linter ```mvn spotless:apply```

### JaCoCo Coverage Report
1) Run ```mvn test```
2) Run ```mvn jacoco:report```
3) Open ```target/site/jacoco/index.html``` in a browser
