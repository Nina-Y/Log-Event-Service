package com.example.logeventservice.controller;

import com.example.logeventservice.model.EventType;
import com.example.logeventservice.model.LogEvent;
import com.example.logeventservice.service.LogEventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller
@RequestMapping("/ui/logs")
public class LogEventUIController {

    private final LogEventService logEventService;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public LogEventUIController(LogEventService logEventService) {
        this.logEventService = logEventService;
    }

    @GetMapping
    public String viewAllLogs(Model model) {
        model.addAttribute("logEvents", logEventService.getAllLogEvents());
        return "logs";
    }

    @GetMapping("/view/{id}")
    public String viewLogDetails(@PathVariable("id") int id, Model model) {
        Optional<LogEvent> logEvent = logEventService.getLogEventById(id);
        if (logEvent.isPresent()) {
            model.addAttribute("logEvent", logEvent.get());
            return "log-details";
        } else {
            return "redirect:/ui/logs";
        }
    }

    @GetMapping("/add")
    public String showAddLogEventForm(Model model) {
        model.addAttribute("logEvent", new LogEvent());
        model.addAttribute("EventType", EventType.values());
        return "add-log";
    }

    @PostMapping("/add")
    public String addLogEvent(@ModelAttribute LogEvent logEvent) {
        logEvent.setTimestamp(LocalDateTime.parse(LocalDateTime.now().format(FORMATTER)));
        return "redirect:/ui/logs";
    }

    @GetMapping("/edit/{id}")
    public String showEditLogEventForm(@PathVariable("id") int id, Model model) {
        LogEvent logEvent = logEventService.getLogEventById(id).orElseThrow(() -> new IllegalArgumentException("Invalid log event ID"));
        model.addAttribute("logEvent", logEvent);
        model.addAttribute("EventType", EventType.values());
        return "edit-log";
    }

    @PostMapping("/edit/{id}")
    public String editLogEvent(@PathVariable("id") int id, @ModelAttribute LogEvent logEvent) {
        logEvent.setTimestamp(LocalDateTime.parse(LocalDateTime.now().format(FORMATTER)));
        logEventService.updateLogEvent(logEvent, id);
        return "redirect:/ui/logs";
    }

    // Delete log event by ID
    @GetMapping("/delete/{id}")
    public String deleteLogEvent(@PathVariable("id") int id) {
        logEventService.deleteLogEventById(id);
        return "redirect:/ui/logs";
    }
}
