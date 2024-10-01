import com.example.logeventservice.data.LogEventData;
import com.example.logeventservice.model.EventType;
import com.example.logeventservice.model.LogEvent;
import com.example.logeventservice.service.LogEventService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LogEventServiceTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private LogEventData logEventData;

    @InjectMocks
    private LogEventService logEventService;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);

        List<LogEvent> dummyLogEvents = new ArrayList<>();
        dummyLogEvents.add(new LogEvent(1, LocalDateTime.now(), EventType.INFO, "Test message 1", "user1", UUID.randomUUID()));
        dummyLogEvents.add(new LogEvent(2, LocalDateTime.now(), EventType.ERROR, "Test message 2", "user2", UUID.randomUUID()));

        when(objectMapper.readValue(any(File.class), any(TypeReference.class))).thenReturn(dummyLogEvents);
        when(logEventData.initializeDummyData(any(), any())).thenReturn(dummyLogEvents);

        logEventService = new LogEventService(objectMapper, logEventData);
    }

    @Test
    void testLoadLogEvents_FileExists() throws IOException {
        assertEquals(2, logEventService.getAllLogEvents().size());
    }

    @Test
    void testAddLogEvent() {
        LogEvent newLogEvent = new LogEvent(0, LocalDateTime.now(), EventType.INFO, "New log event", "user3", UUID.randomUUID());

        LogEvent addedEvent = logEventService.addLogEvent(newLogEvent);

        assertEquals(3, addedEvent.getId());
        assertEquals(EventType.INFO, addedEvent.getType());
        assertEquals(3, logEventService.getAllLogEvents().size());
    }

    @Test
    void testDeleteLogEventById() {
        logEventService.deleteLogEventById(1);

        assertEquals(1, logEventService.getAllLogEvents().size());
        assertFalse(logEventService.getLogEventById(1).isPresent());
    }

    @Test
    void testDeleteAllLogEvents() {
        logEventService.deleteAllLogEvents();

        assertEquals(0, logEventService.getAllLogEvents().size());
    }

    @Test
    void testUpdateLogEvent() {
        LogEvent updatedLogEvent = new LogEvent(2, LocalDateTime.now(), EventType.DEBUG, "Updated message", "updatedUser", UUID.randomUUID());

        logEventService.updateLogEvent(updatedLogEvent, 2);

        Optional<LogEvent> eventOptional = logEventService.getLogEventById(2);
        assertTrue(eventOptional.isPresent());

        LogEvent event = eventOptional.get();
        assertEquals(EventType.DEBUG, event.getType());
        assertEquals("Updated message", event.getMessage());
        assertEquals("updatedUser", event.getUserId());
    }

    @Test
    void testGenerateNextId() {
        assertEquals(3, logEventService.generateNextId());
    }

    @Test
    void testGetLogEventById() {
        Optional<LogEvent> logEventOptional = logEventService.getLogEventById(1);

        assertTrue(logEventOptional.isPresent());
        LogEvent logEvent = logEventOptional.get();
        assertEquals(1, logEvent.getId());
        assertEquals("Test message 1", logEvent.getMessage());
    }

    @Test
    void testGetAllLogEvents() {
        assertEquals(2, logEventService.getAllLogEvents().size());

        LogEvent newLogEvent = new LogEvent(0, LocalDateTime.now(), EventType.INFO, "New log event", "user3", UUID.randomUUID());
        logEventService.addLogEvent(newLogEvent);

        assertEquals(3, logEventService.getAllLogEvents().size());
    }
}