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

## Prerequisites
- Java SDK (Java 11 or 12 should do fine). [Download here.](https://www.oracle.com/technetwork/java/javase/downloads/index.html)
- IntelliJ (Community Edition). [Download here.](https://www.jetbrains.com/idea/download/)
- [Docker for Windows](https://hub.docker.com/editions/community/docker-ce-desktop-windows) or [Docker for Mac](https://hub.docker.com/editions/community/docker-ce-desktop-mac).
- Maven. `brew install maven` on Mac and `choco install maven`. ([Install Homebrew](https://brew.sh/index_sv) or [Chocolatey](https://chocolatey.org/docs/installation) if you haven't already.)
- Optional: PGAdmin. [Download here.](https://www.pgadmin.org/download/)

## Getting started
- Clone this repository: `git clone https://github.com/alexandersundstrom/rest.git`
- Start the database:
  - `cd path/to/repo`
  - `docker-compose up -d`
- Start the application:
  - In IntelliJ:
    - Open the project in IntelliJ
    - Click dropdown in top-right corner > "Edit configurations"
    - Click plus sign (`+`) in top-left corner > Select "Application"
    - Give the configuration a name (e. g. "Run app")
    - Click the three dots (`...`) next to "Main class" and select the class named `Application.java`
    - Click "OK" to close the dialog and then click "Run" or "Debug" in the top-right corner.
  - From terminal:
    - `cd /path/to/repo`
    - `mvn clean package` (only first time and when changes has been done)
    - `java -jar target/rest-api-1.0-SNAPSHOT.jar`
- Visit [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) to verify that you're done.

Optional: Inspect your database in PGAdmin
- Start PGAdmin (from Start Menu or Application Launcher)
- Right-click "Servers" and select "Create" > "Server"
- Give it a name (e. g. "rest-workshop")
- Click "Connection" tab
  - Host: localhost
  - Username: springbootuser
  - Password: admin
