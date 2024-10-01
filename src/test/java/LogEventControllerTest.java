import com.example.logeventservice.controller.LogEventController;
import com.example.logeventservice.model.EventType;
import com.example.logeventservice.model.LogEvent;
import com.example.logeventservice.service.LogEventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class LogEventControllerTest {

    @Mock
    private LogEventService logEventService;

    @InjectMocks
    private LogEventController logEventController;

    private List<LogEvent> dummyLogEvents;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        dummyLogEvents = new ArrayList<>();
        dummyLogEvents.add(new LogEvent(1, LocalDateTime.now(), EventType.INFO, "Test message 1", "user1", UUID.randomUUID()));
        dummyLogEvents.add(new LogEvent(2, LocalDateTime.now(), EventType.ERROR, "Test message 2", "user2", UUID.randomUUID()));
    }

    @Test
    void testGetAllLogEvents() {
        when(logEventService.getAllLogEvents()).thenReturn(dummyLogEvents);

        List<LogEvent> result = logEventController.getAllLogEvents();

        assertEquals(2, result.size());
        verify(logEventService, times(1)).getAllLogEvents();
    }

    @Test
    void testGetLogEventById_Found() {
        when(logEventService.getLogEventById(1)).thenReturn(Optional.of(dummyLogEvents.get(0)));

        ResponseEntity<LogEvent> response = logEventController.getLogEventById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dummyLogEvents.get(0), response.getBody());
        verify(logEventService, times(1)).getLogEventById(1);
    }

    @Test
    void testGetLogEventById_NotFound() {
        when(logEventService.getLogEventById(anyInt())).thenReturn(Optional.empty());

        ResponseEntity<LogEvent> response = logEventController.getLogEventById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(logEventService, times(1)).getLogEventById(1);
    }

    @Test
    void testCreateLogEvent() {
        LogEvent newLogEvent = new LogEvent(0, LocalDateTime.now(), EventType.INFO, "New log event", "user3", UUID.randomUUID());

        when(logEventService.addLogEvent(any(LogEvent.class))).thenReturn(newLogEvent);

        ResponseEntity<LogEvent> response = logEventController.createLogEvent(newLogEvent);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(newLogEvent, response.getBody());
        verify(logEventService, times(1)).addLogEvent(newLogEvent);
    }

    @Test
    void testUpdateLogEvent() {
        ResponseEntity<Void> response = logEventController.updateLogEvent(dummyLogEvents.get(0), 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(logEventService, times(1)).updateLogEvent(dummyLogEvents.get(0), 1);
    }

    @Test
    void testDeleteAllLogEvents() {
        ResponseEntity<Void> response = logEventController.deleteAllLogEvents();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(logEventService, times(1)).deleteAllLogEvents();
    }

    @Test
    void testDeleteLogEventById() {
        ResponseEntity<Void> response = logEventController.deleteLogEventById(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(logEventService, times(1)).deleteLogEventById(1);
    }
}