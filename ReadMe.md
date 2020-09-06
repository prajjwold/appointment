### Prerequisites

You need to install following softwares before you are able to run this application on your build machine

```
* Java 1.11
* Maven
```

### Running Server

Run following command to start the server:

```
mvn spring-boot:run
```

## Accessing swagger ui

To access the swagger ui, use the url mentioned below:
```
http://localhost:8080/swagger-ui.html
```

## Accessing in memory H2-Database

To access the embedded inmemory database (i.e. H2 DB), use the following URL:
```
http://localhost:8080/h2-console/
```
db connection string=jdbc:h2:mem:appointment
username=sa
password=

       
## Authors

* **Prajjwol Dandekhya**