package com.example.logeventservice.controller;

import com.example.logeventservice.model.LogEvent;
import com.example.logeventservice.service.LogEventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/logs")
@Validated
public class LogEventController {

    private final LogEventService logEventService;

    public LogEventController(LogEventService logEventService) {
        this.logEventService = logEventService;
    }

    // Tools like Swagger/ Postman can be used, eg. http://localhost:8080/swagger-ui/index.html

    @GetMapping
    public List<LogEvent> getAllLogEvents() {
        return logEventService.getAllLogEvents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LogEvent> getLogEventById(@PathVariable int id) {
        Optional<LogEvent> logEvent = logEventService.getLogEventById(id);
        return logEvent.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<LogEvent> createLogEvent(@Valid @RequestBody LogEvent logEvent) {
        LogEvent createdEvent = logEventService.addLogEvent(logEvent);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateLogEvent(@Valid @RequestBody LogEvent logEvent, @PathVariable int id) {
        logEventService.updateLogEvent(logEvent, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllLogEvents() {
        logEventService.deleteAllLogEvents();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLogEventById(@PathVariable int id) {
        logEventService.deleteLogEventById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}