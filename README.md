# Employee REST Application

The purpose of this task is create a Web application that exposes REST CRUD operations for employees.

# Features
  - The app uses Spring boot as run as a microservice
  - It handles execution profiles (prod, dev, test, default)
  - Works with Java 8+ (uses Lambda Expressions, streams, method reference)
  - Implements MVC, DTO design patterns
  - Security with Open Authorization (OAuth) and JSON Web tokens (JWT)
  - In memory H2 DB
  - Thymeleaf template engine for the View layer
  - test cases
  - logging using Logback
  - three ways to test the REST APIs: custom GUI, Swagger UI, and direct APIs call

# Getting Started
In order to run the app, please follow below steps:

1. Download the source code from the repository:
```sh
$ git clone https://github.com/danielmxuk/k3nz4n_employee_app.git
```

2. Compile the code (without test task, it is important because the test task includes a test that needs the app running separately)
```sh
$ gradle clean build customFatJar -x test 
```

3. Run the app by etiher way with Gladle or directly calling the app:
```sh
$ java -jar -Dspring.profiles.active=prod  build/libs/employee_rest_app-1.0.1-SNAPSHOT.jar

or 

$ gradle bootRun
```

# Running the Employee app
Once the application has started below message is showed:
```sh

  -> Loaded Employee data: (300 generated) (300 saved)   -  App ready to use, have fun :)

```

Now the app can be readed from a Web browser as:
http://localhost:8080/employee-app/

[![Login](./src/main/resources/app_screenshots/screenshot_employee_app_login.jpg)]


# Tests
  - 


# Technical details
  - 


# Author
* **Daniel Beltran Roman** - [mail](mailto:danielmxuk@gmail.com)
