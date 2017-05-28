# Skills in Spring, Data manipulation and JAX RS

Proposed solution to the exercise.

# Expected results
The application should run after the following command line:

	java -jar target/spring-jpa-jersey.jar
    
or 

    mvn spring-boot:run

Then, open a browser and type:

    http://localhost:8090/rest/cities?country=Uni

It must return, at least the following:

    [
        {
            "id":86,
            "name":"New York",
            "country":{
                "id":2,
                "name":"United States"
            }
        },
        {
            "id":87,
            "name":"Los Angeles",
            "country":{
                "id":2,
                "name":"United States"
            }
        },
        {
            "id":88,
            "name":"Atlanta",
            "country":{
                "id":2,
                "name":"United States"
            }
        }
    ]

The load cities feature is implemented through REST, accepting a JSON array of cities as input. The input does not need IDs, that are ignored if provided (names are used as unity criteria). The **curl** tool can be used for testing the POST operation:

    curl -v -H "Content-Type: application/json" -d '[{"name": "Belo Horizonte", "country": {"name": "Brazil"}}]' http://localhost:8090/rest/cities 

The operation to load the data into database itself is asynchronous, so the expected response code is 202 (Accepted).

The following request is invalid (missing city name):

    curl -H "Content-Type: application/json" -d '[{"name": null, "country": {"name": "Brazil"}}]' http://localhost:8090/rest/cities

So the response code must be 404 (Bad Request) with the payload:

    [{"index":0,"message":"name may not be empty"}]

# Implementation details

- Dependencies versions were updated. This caused some changes in Spring Context declaration of JUnit tests. Details: https://spring.io/blog/2016/04/15/testing-improvements-in-spring-boot-1-4 
- Application class was moved to the root package, so Spring can load all the component without explicit package declaration
- Load cities feature was implemented asynchronously through Spring Async capabilities. It could also had done with messaging, for example, using AQMP protocol. First option was chosen to avoid external dependency of an AQMP server, like RabbitMQ.  
- The existing tests were moved to the same package of its testing class, to provide code coverage metrics using IntelliJ or Jacoco.
- The findLikeName method in Country repository was created just to meet existing tests compliance (TDD). The corresponding implementation using Spring Data convention would be findByNameLike. 

# Future improvements

As described in a TODO into CityResource class, it can be necessary to add a route for querying the processing result of load cities operations, that is asynchronous.

It can be done, for example, by generating a random UUID and deliver it to the client as a result of the accepted request.

With this UUID, the client can perform a second request to know details of load data processing results.

To do this in a way that the service can continue scaling, with cluster capabilities, a Key-Value or Document-Oriented NOSQL database (like Redis or MongoDB) can be used to store the processing information mapped by the UUID.

A second improvement is to create a Dockerfile to provide container deployment.