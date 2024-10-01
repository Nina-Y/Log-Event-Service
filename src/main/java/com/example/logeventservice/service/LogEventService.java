package com.example.logeventservice.service;

import com.example.logeventservice.data.LogEventData;
import com.example.logeventservice.model.LogEvent;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LogEventService {

    private static final String FILE_PATH = "log_events.json";
    private List<LogEvent> logEvents = new ArrayList<>();
    private final ObjectMapper objectMapper;
    private final LogEventData logEventData;

    public LogEventService(ObjectMapper objectMapper, LogEventData logEventData) {
        this.objectMapper = objectMapper;
        this.logEventData = logEventData;
        loadLogEvents();
    }

    public void loadLogEvents() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            System.out.println("File already exists at: " + FILE_PATH);
            try {
                logEvents = objectMapper.readValue(file, new TypeReference<>() {});
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File does not exist. Creating new file with dummy data at: " + FILE_PATH);
                logEvents = logEventData.initializeDummyData(this::generateNextId, logEvents);
                saveLogEvents();
        }
    }

    private void saveLogEvents() {
        try {
            objectMapper.writeValue(new File(FILE_PATH), logEvents);
            System.out.println("Log events saved to file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<LogEvent> getAllLogEvents() {
        return logEvents;
    }

    public Optional<LogEvent> getLogEventById(int id) {
        return logEvents.stream().filter(event -> event.getId() == id).findFirst();
    }

    public LogEvent addLogEvent(LogEvent logEvent) {
        logEvent.setId(generateNextId());
        logEvents.add(logEvent);
        saveLogEvents();
        return logEvent;
    }

    public void deleteAllLogEvents() {
        logEvents.clear();
        saveLogEvents();
    }

    public void deleteLogEventById(int id) {
        logEvents.removeIf(event -> event.getId() == id);
        saveLogEvents();
    }

    public void updateLogEvent(LogEvent logEvent, int id) {
        Optional<LogEvent> existingEvent = getLogEventById(id);
        existingEvent.ifPresent(event -> {
            event.setTimestamp(logEvent.getTimestamp());
            event.setType(logEvent.getType());
            event.setMessage(logEvent.getMessage());
            event.setUserId(logEvent.getUserId());
            event.setTransactionId(logEvent.getTransactionId());
            saveLogEvents();
        });
    }

    public int generateNextId() {
        return logEvents.stream().mapToInt(LogEvent::getId).max().orElse(0) + 1;
    }
}