# superdevs_task

The project is responsible for exposing API for querying the underlying datastore. Since there were no strict requirements I decided to implement 'sample queries' plus two additional that
provides the datastores with campaigns having clicks above the threshold and 2nd on calculating CTR per daily for the datasource. The API validates in simple way provided request parameters
by checking their existence and proper date format.

## Implementation
The task was implemented using following tools
- spring boot 2.4.3
- apache spark 3.1.0
- java 8

Since in this case having Apache Spark may look like an overkill I wanted to include it to present my ability to use that tool. In my opinion it is powerful tool and works great when it comes
to execute queries on CSV data.

Documentation may be found under http://localhost:8080/swagger-ui.html page. It describes exposed endpoints with accepted parameters (optional or not),returned respones and possible http codes.

In order to test both business and web layers the spring-boot-test library has been used to properly mock behavior of the rest controllers. Business layer was tested by setting up test spark context.

## How-TO
In order to build application execute
`mvn clean install`
It will produce jar file under target directory.

To execute unit tests located under the project
`mvn test`

To start the application locally
`nohup java -jar target/superdevs-task-0.0.1-SNAPSHOT.jar > log.txt  2>&1 &`
That will also redirect all the logs to log.txt file.

## Areas to improve
Of course there are some areas to improve. For this moment I can imagine that e.g.
- application may be dockerized using e.g. JIB https://github.com/GoogleContainerTools/jib/tree/master/jib-maven-plugin
- introduce common validator for request parameters using http://hibernate.org/validator/
- implement caching since I expect the datastore is updated daily so there is no point of calculating everyday the same queries but the result per query may be stored in the cache