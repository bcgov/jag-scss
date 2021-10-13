FROM openjdk:11-jre-slim
RUN apt-get update && apt-get install -y curl
RUN curl -O https://archive.apache.org/dist/jmeter/binaries/apache-jmeter-5.3.tgz
RUN tar -xvf apache-jmeter-5.3.tgz
RUN rm apache-jmeter-5.3.tgz
RUN chmod 777 apache-jmeter-5.3
ADD load_test apache-jmeter-5.3/
RUN chmod +x apache-jmeter-5.3/load_test
