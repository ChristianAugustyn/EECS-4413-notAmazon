# notAmazon

### Frontend
[EECS-4413-not-amazon-client](https://github.com/ChristianAugustyn/EECS-4413-not-amazon-client/blob/master/README.md)

## How to setup the project

- pull the repository onto your machine using

```bash
$ git clone https://github.com/ChristianAugustyn/EECS-4413-notAmazon.git
```

- make sure to have a Tomcat server installed and ready
- Be sure to change the URL in WebContent > META-INF > Context.xml on line 9 to the location for your directory
- right click on the project in eclipse and select "run on server"

## How to run tests

- from the zip file containing all the projects specification, import the project labeled as "notAmazon_test"
- select to "run" the class labeled RestClient.java
- all output from the endpoints will appear as strings in the console. some of the endpoints only return the status and result in not having anything show up.
