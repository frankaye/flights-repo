
manual test case 


//GET : http://<server url>/math/add?n1=<numeric param 1>&n2=<numeric param 2>

curl -X GET "http://localhost:8080/flights-app/math/add?n1=12.3&n2=16.6"

//POST : http://<server url>/math/add (allow for form params 1&2 in a POST body)

curl -v -s -d "n1=23.31&n2=26.21" -H "Content-Type: application/x-www-form-urlencoded" -X POST http://localhost:8080/flights-app/math/add

//GET : http://<server url>/airports

curl -X GET http://localhost:8080/flights-app/airports


flights-client 
The flights-clients contains Asynchronous RESTful web service client. The RESTEasy client make http request to remote RESTful web services. The RESTEasy client could be invoked from IDE. 


