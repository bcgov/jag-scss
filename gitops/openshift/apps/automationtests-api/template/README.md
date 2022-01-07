## Templates to create openshift components related to jag-scss-tests api deployment

### Command to execute template
1) Login to OC using login command
2) Run below command in each env. namespace dev/test/prod
   ``oc process -f jag-scss-tests.yaml --param-file=jag-scss-tests.env | oc apply -f -``


