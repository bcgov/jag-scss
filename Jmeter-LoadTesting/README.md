# Setting up jmeter for load testing

## Creating the Openshift resources

1) Apply the jmeter manager deployment file
2) Apply the jmeter worker yml
3) Apply the influx yml
   1) On the influx pod run ```influx``` to open the influx cli. This can take a few minutes to complete
   2) Run ```Create Database jmeter```
4) Apply the Grafana yml 
   1) Create the data source
      1) Through the UI in Grafana 
      2) Fill in the template and run from inside the cluster. Note: Nginx pods are guaranteed to have curl  ```curl 'http://<user>:<password>@jmeter-grafana:3000/api/datasources' -X POST -H 'Content-Type: application/json;charset=UTF-8' --data-binary '{"name":"jmeterdb","type":"influxdb","url":"http://jmeter-influxdb:8086","access":"proxy","isDefault":true,"database":"jmeter","<user>":"admin","password":"<password>"}'```

## Updating Docker images for jmeter

Note: influx db has large changes from v1.X to v2.X. This project is currently running influx v1.8.9 
changing to v2 would require significant configuration changes. 

1) Modify the Dockerfile with your changes
2) Change to Dockerfile directory
3) ```docker login <opensift address>/<openshift namesapce> -u <openshift username>``` at the prompt enter your openshift token 
4) ```docker build . -t <opensift address>/<openshift namesapce>/<image name>:<version>``` This builds and tags the image
5) ```docker push <opensift address>/<openshift namesapce>/<image name>:<version>``` This pushes the image to openshift and should auto update the deployments with the new images

## Running a load test

**Note: Always follow the etiquette for running load tests**

1) ```oc cp <testFile>.jmx <jmeter-master>:/apache-jmeter-5.3/<testFile>.jmx``` This copies the test file from the local computer to the jmeter master pod
2) Running the test file 
   1) From oc cli ```oc exec -ti <jmeter-master> -- /bin/bash /apache-jmeter-5.3/load_test <testFile>.jmx```
   2) From the pod terminal ```./load_test <testFile>.jmx```
3) Getting the results
   1) Depending on your jmx file you can output the results to a csv and you can also output to the influx with a 
   listener so that it get displayed in grafana
   2) To get the file use ```oc cp <src> <dst>```

## Editing the JMX file
1) Download apache jmeter 5.3 if not installed 
2) Run ```\apache-jmeter-5.4.1\bin\ApacheJmeter.jar```
   1) Outputting results to a file requires a View Tree Results listener
   2) Outputting to influx requires a Backend Listener configured to use GraphiteBackendListener with the influx db as the graphite host
   3) Setting summary to true will greatly reduce the amount of information record 