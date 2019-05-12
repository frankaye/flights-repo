
flights-app

flights-app provides RESTful web services. The flights-app is RESTEasy and spring-boot application. It could be used as skeleton for async response development with REASTeasy and Spring-boot together. 

Build
run following command line in the project folder

mvn install

mvn spring-boot:run

RESTfull web services with the following path:

for example with localhost:

//GET : http://<server url>/math/add?n1=<numeric param 1>&n2=<numeric param 2>
http://localhost:8080/flights-app/math/add?n1=12.3&n2=16.6


//POST : http://<server url>/math/add (allow for form params 1&2 in a POST body)
http://localhost:8080/flights-app/math/add

//GET : http://<server url>/airports
http://localhost:8080/flights-app/airports


Data scenarios with flights-app

the following sample json from api.flightstats.com is used 

https://api.flightstats.com/flex/airports/docs/v1/lts/samples/Airports_response.json

In order to test with performance the following json is used to calculate how long to load the data. 

https://datahub.io/core/airport-codes

https://pkgstore.datahub.io/core/airport-codes/airport-codes_json/data/f32b003723518668d730938f50c50efa/airport-codes_json.json

For above data with around 16MB, it average takes about 12 seconds to load from flights-app application with the above URL. 





