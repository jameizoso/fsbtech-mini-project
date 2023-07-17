# fsbtech-mini-project

# Requirements
To run this application, make sure you have the following:

Java Development Kit (JDK)
Apache Maven

# Technologies Used
The application is developed using the following technologies:

Java 11
Spring Framework
Maven
Getting Started
To set up and run the application, follow these steps:

Clone the repository:

run command
git clone https://github.com/jameizoso/fsbtech-mini-project.git
Navigate to the project directory:

bash
Copy code
cd game-management-application
Build the project using Maven:

run command
mvn clean install
Run the application:

run command
mvn spring-boot:run
The application will start running locally at http://localhost:8080.

#  API Documentation

Use http://localhost:8080/swagger-ui.html 

The application provides the following REST API endpoints: 

GET /games: Retrieves all games  
GET /games/{id}: Retrieves a specific game by ID  
POST /games: Creates a new game  
PUT /games/{id}: Updates an existing game  
DELETE /games/{id}: Deletes a game  


Refer to the API documentation for detailed information about request/response formats and examples.

#  Testing
The application includes unit tests with Junit and Mockito to ensure the proper functioning of the GameService and other components. You can run the tests using the following command:

run command
mvn test

# Logging
Logging is implemented in the application to capture important events and provide insights into the system's behavior. Events are showed in the console

# Documentation
The application is documented using Javadoc comments to provide comprehensive information about the classes, methods, and their usage. You can generate the documentation using the following command:

run command
mvn javadoc:javadoc
The generated documentation can be found in the target/site/apidocs directory.

# Dependencies
The project is managed using Maven and includes the following dependencies:

**com.h2database:** This dependency provides support for the H2 in-memory database. It is used for testing and development purposes, allowing the application to interact with an embedded database.

**springfox-swagger2:** This dependency enables the generation of API documentation using Swagger 2. It provides annotations and tools to create comprehensive and interactive documentation for the REST API endpoints.

**junit-jupiter:** This dependency includes the JUnit Jupiter testing framework. It provides a rich set of annotations, assertions, and test execution mechanisms to write unit tests for the application's components.

**org.projectlombok:** Lombok is a Java library that helps reduce boilerplate code by automatically generating getter, setter, and other utility methods. It enhances code readability and maintainability by reducing the need for writing repetitive code.

**spring-boot-starter-cache:** This dependency provides support for caching in Spring Boot applications. It includes the necessary components and configurations to enable caching of method results, improving the application's performance by reducing redundant computations.

# Conclusion
This application provides a robust solution for managing games in a game service provider environment. It utilizes Java, Spring, and Maven to deliver efficient game operations, including CRUD functionality and a memory cache structure. The application is designed to handle concurrency and considers performance implications. Additionally, it incorporates testing, logging, and documentation features to ensure the application's reliability and maintainability.

For any further information or assistance, please refer to the project's documentation or contact the development team.

