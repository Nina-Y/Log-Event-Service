import com.example.logeventservice.controller.LogEventUIController;
import com.example.logeventservice.model.EventType;
import com.example.logeventservice.model.LogEvent;
import com.example.logeventservice.service.LogEventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LogEventUIControllerTest {

    @Mock
    private LogEventService logEventService;

    @Mock
    private Model model;

    @InjectMocks
    private LogEventUIController logEventUIController;

    private List<LogEvent> dummyLogEvents;
    private LogEvent dummyLogEvent;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        dummyLogEvents = new ArrayList<>();
        dummyLogEvent = new LogEvent(1, LocalDateTime.now(), EventType.INFO, "Test message", "user1", UUID.randomUUID());
        dummyLogEvents.add(dummyLogEvent);
    }

    @Test
    public void testViewAllLogs() {
        when(logEventService.getAllLogEvents()).thenReturn(dummyLogEvents);

        String viewName = logEventUIController.viewAllLogs(model);

        verify(logEventService, times(1)).getAllLogEvents();
        verify(model, times(1)).addAttribute(eq("logEvents"), eq(dummyLogEvents));

        assertEquals("logs", viewName);
    }

    @Test
    public void testViewLogDetailsWhenLogExists() {
        when(logEventService.getLogEventById(1)).thenReturn(Optional.of(dummyLogEvent));

        String viewName = logEventUIController.viewLogDetails(1, model);

        verify(logEventService, times(1)).getLogEventById(1);
        verify(model, times(1)).addAttribute(eq("logEvent"), eq(dummyLogEvent));

        assertEquals("log-details", viewName);
    }

    @Test
    public void testViewLogDetailsWhenLogDoesNotExist() {
        when(logEventService.getLogEventById(1)).thenReturn(Optional.empty());

        String viewName = logEventUIController.viewLogDetails(1, model);

        verify(logEventService, times(1)).getLogEventById(1);

        assertEquals("redirect:/ui/logs", viewName);
    }

    @Test
    public void testShowAddLogEventForm() {
        String viewName = logEventUIController.showAddLogEventForm(model);

        verify(model, times(1)).addAttribute(eq("logEvent"), any(LogEvent.class));
        verify(model, times(1)).addAttribute(eq("EventType"), eq(EventType.values()));

        assertEquals("add-log", viewName);
    }

    @Test
    public void testAddLogEvent() {
        LogEvent newLogEvent = new LogEvent(2, LocalDateTime.now(), EventType.WARNING, "New message", "user2", UUID.randomUUID());
        String viewName = logEventUIController.addLogEvent(newLogEvent);

        verify(logEventService, times(1)).addLogEvent(newLogEvent);

        assertEquals("redirect:/ui/logs", viewName);
    }

    @Test
    public void testShowEditLogEventForm() {
        when(logEventService.getLogEventById(1)).thenReturn(Optional.of(dummyLogEvent));

        String viewName = logEventUIController.showEditLogEventForm(1, model);

        verify(logEventService, times(1)).getLogEventById(1);
        verify(model, times(1)).addAttribute(eq("logEvent"), eq(dummyLogEvent));
        verify(model, times(1)).addAttribute(eq("EventType"), eq(EventType.values()));

        assertEquals("edit-log", viewName);
    }

    @Test
    public void testEditLogEvent() {
        LogEvent updatedLogEvent = new LogEvent(1, LocalDateTime.now(), EventType.ERROR, "Updated message", "user1", UUID.randomUUID());
        String viewName = logEventUIController.editLogEvent(1, updatedLogEvent);

        verify(logEventService, times(1)).updateLogEvent(updatedLogEvent, 1);

        assertEquals("redirect:/ui/logs", viewName);
    }

    @Test
    public void testDeleteLogEvent() {
        String viewName = logEventUIController.deleteLogEvent(1);

        verify(logEventService, times(1)).deleteLogEventById(1);

        assertEquals("redirect:/ui/logs", viewName);
    }
}

