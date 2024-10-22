package com.example.logeventservice.controller;

import com.example.logeventservice.model.EventType;
import com.example.logeventservice.model.LogEvent;
import com.example.logeventservice.service.LogEventService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/ui/logs")

public class LogEventUIController {

    private final LogEventService logEventService;

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
    public String addLogEvent(@Valid @ModelAttribute("logEvent") LogEvent logEvent, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("EventType", EventType.values());
            return "add-log";
        }
        logEventService.addLogEvent(logEvent);
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
    public String editLogEvent(@PathVariable("id") int id, @Valid @ModelAttribute("logEvent") LogEvent logEvent, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("EventType", EventType.values());
            return "edit-log";
        }
        logEventService.updateLogEvent(logEvent, id);
        return "redirect:/ui/logs";
    }

    @GetMapping("/delete/{id}")
    public String deleteLogEvent(@PathVariable("id") int id) {
        logEventService.deleteLogEventById(id);
        return "redirect:/ui/logs";
    }
}