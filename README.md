# REST

## Introduction
This repo uses [Spring Boot](https://spring.io/projects/spring-boot) to set up a simple REST API using two entities 
(Person and Phone).

[Swagger](https://swagger.io/) is used for documentation and manual tests of the REST API

Models are managed using JPA (Java Persistence API) and ORM (Object Relational Mapping). For access to the database,
Spring Frameworks [CrudRepository](https://docs.spring.io/spring-data/data-commons/docs/1.6.1.RELEASE/reference/html/repositories.html) is used.

Models are persisted in [Postgresql](https://www.postgresql.org/). Spring Boot will create tables and column if they 
don't exist.

[Annotations](https://en.wikipedia.org/wiki/Java_annotation) are used for springing up the entire application and 
connects the models to the database.

All dependencies are handled using [Maven](https://maven.apache.org/).

## Requirements
A postgresql database needs to be running with the credentials specified in `application.properties`. Update them if needed.

## Startup
To start the application:
* Run `Application.java`
* Access [Swagger UI](http://localhost:8080/swagger-ui.html#) to view all endpoints and test their functionality.