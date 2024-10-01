## Log Event Service
This project is a Spring Boot application designed to manage log events. It provides both a RESTful API and a user interface to create, retrieve, update, and delete log events. Log events are stored in a JSON file (log_events.json) and can be managed through the available endpoints or via a web interface.

### Features
- Create new log events
- Retrieve all log events or by ID
- Update existing log events
- Delete log events (all or by ID)
- Persistence of log events in a log_events.json file
- Web UI for managing log events 
- RESTful API for managing log events

### Technologies Used
- Java 21
- Spring Boot
- Thymeleaf for UI templating
- Jackson for JSON processing
- JUnit and Mockito for unit testing
- Maven for project management

### Running the Application
You can run the application with:
- mvn spring-boot:run

The web UI is accessible at http://localhost:8080/ui/logs