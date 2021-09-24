## Templates to create openshift components related to jag-scss api deployment

### Command to execute template
1) Login to OC using login command
2) Run below command in each env. namespace dev/test/prod/tools
   ``oc process -f scss_jag.yaml --param-file=scss_jag.env | oc create -f -``


