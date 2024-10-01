package com.example.logeventservice.data;

import com.example.logeventservice.model.EventType;
import com.example.logeventservice.model.LogEvent;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class LogEventData {

    public List<LogEvent> initializeDummyData(NextIdGenerator generateNextId, List<LogEvent> logEvents) {
        logEvents.add(new LogEvent(generateNextId.generate(), LocalDateTime.of(2024, 3, 18, 21, 0, 5), EventType.DEBUG, "Data stored", "7P96R4", UUID.randomUUID()));
        logEvents.add(new LogEvent(generateNextId.generate(), LocalDateTime.of(2024, 3, 20, 9, 10, 47), EventType.INFO, "Connection was successful", "642Q4T8", UUID.randomUUID()));
        logEvents.add(new LogEvent(generateNextId.generate(), LocalDateTime.of(2024, 3, 20, 14, 58, 2), EventType.WARNING, "Missing parameter", "632V47", UUID.randomUUID()));
        logEvents.add(new LogEvent(generateNextId.generate(), LocalDateTime.of(2024, 3, 27, 18, 13, 59), EventType.ERROR, "Exception: Failed to store data", "451136", UUID.randomUUID()));
        return logEvents;
    }

    @FunctionalInterface
    public interface NextIdGenerator {
        int generate();
    }
}