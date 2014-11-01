# Basic REST API

A RESTful API, designed for performing basic CRUD operations on dynamic JSON objects.

_Copyright 2014 Shaun Scovil_


### Ingredients

This project was developed with __Java 1.7__, using __Maven 3.2.3__ and the __Jersey Maven archetype__:

* Artifact ID: `jersey-quickstart-grizzly2`
* Group ID: `org.glassfish.jersey.archetypes`
* Version: `2.13`

It has the following dependencies, included in the JAR thanks to the __Shade Maven plugin__:

* Grizzly HTTP Server 2.3.16
* Jackson FasterXML 2.4.3
* MongoDB driver 2.12.4
* JUnit 4.9
* Mockito 1.10.8


### Getting Started

In order to run this API, you will need to do the following (more detailed instructions below):

1. Clone this repository
2. Configure the application
3. Set the environment to DEVELOPMENT, STAGING or PRODUCTION
4. Run the Maven build script

Following those steps will generate a fat JAR that contains the API and all of it's dependencies.


### Prerequisites

To build and run the API, you will need Java 1.7 and Maven 3.x installed. In addition, you will need access to a running instance of MongoDB >= 2.4 (older versions may work, but are untested).

* [Download Java](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)
* [Download Maven](http://maven.apache.org/download.cgi)
* [Download MongoDB](http://www.mongodb.org/downloads)


### Configuration

Located in `src/main/resources` are three configuration files:

* `development.properties`
* `staging.properties`
* `production.properties`

Each file has the follow properties:

name | description | default value
-----|-------------|--------------
api.baseURI | The base URI for the application (ex: `http://api.shaunscovil.com/api`). | Required (no default).
api.data.mongodb.host | The hostname or IP address of your MongoDB instance. | `localhost`
api.data.mongodb.port | The port number of your MongoDB instance. | `27017`
api.data.mongodb.username | MongoDB username with read/write access to the dbname specified. | `root`
api.data.mongodb.authDB | MongoDB database used to authenticate the username. | `admin`
api.data.mongodb.password | MongoDB password. If omitted, authentication will not be used. | None (see description).
api.data.mongodb.dbname | The name of the MongoDB database to use. | `test`


### Environment

In the Main method (`src/main/java/com/shaunscovil/api/Main.java`), look for the following on line 11:

```
public static final Properties PROPERTIES = Configuration.getProperties(Environment.DEVELOPMENT);
```

By changing `DEVELOPMENT` to either `STAGING` or `PRODUCTION`, the application will use the corresponding configuration file (see above).


### Build the JAR

From the command line, navigate to the project's root directory and run the Maven build script:

```
$ mvn clean package
```

Doing so will build a fat JAR file containing the project and all of it's dependencies, which will be located in the `target` directory.


### Run the JAR

From the command line, navigate to the project's root directory and run the JAR using the following command:

```
$ java -jar target/shaunscovil-api-1.0.0-SNAPSHOT.jar
```

In order to run the JAR as a service on Ubuntu, create an [Upstart script](http://upstart.ubuntu.com/getting-started.html) like this:

```
description "Basic REST API"
author "Shaun Scovil"

start on runlevel [3]
stop on shutdown

expect fork

script
    cd /var/www/API
    java -jar /var/www/API/target/shaunscovil-api-1.0.0-SNAPSHOT.jar USERNAME >/var/log/api-shaunscovil.log 2>&1
    emit api-shaunscovil_running
end script
```

Save the file as `/etc/init/rest-api.conf`, then run it using:

```
$ sudo service rest-api start
```


### TODO

This is a hobby project that I intend to continue working on in my abundance of spare time (he said sarcastically). Here are some things I would like to do next:

* __Logging.__ I included `log4j` as a Maven dependency, but am considering alternatives and haven't decided how I want to handle logging yet.
* __Testing.__ You can never have too many tests. Right now, I don't have enough. I definitely want to add code coverage for the data layer and common classes.
* __Load Testing.__ The HTTP server has not been configured or optimized in any way, and I have not yet begun to beat on this thing to see how it handles concurrent requests.
* __Authentication.__ Hello, world! I want to add support for Basic Auth, at the very least.
* __Interfaces.__ As it stands, the data layer is pretty tightly coupled to MongoDB. I want to program the DAO to an interface, making it easier to swap or use multiple DBMSs.
