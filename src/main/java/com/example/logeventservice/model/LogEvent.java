package com.example.logeventservice.model;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.UUID;

import java.time.LocalDateTime;

public class LogEvent {

    private int id;

    @NotNull(message = "Timestamp is required")
    @PastOrPresent(message = "Timestamp must be in the past or present")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    @NotNull(message = "Type is required")
    private EventType type;

    @NotBlank(message = "Message is required")
    @Size(max = 50, message = "Message cannot exceed 50 characters")
    private String message;

    @NotBlank(message = "User ID required")
    @Pattern(regexp = "^[a-zA-Z0-9]{1,6}$")
    private String userId;

    @NotNull(message = "Transaction ID is required")
    private UUID transactionId;

    public LogEvent(int id, LocalDateTime timestamp, EventType type, String message, String userId, UUID transactionId) {
        this.id = id;
        this.timestamp = timestamp;
        this.type = type;
        this.message = message;
        this.userId = userId;
        this.transactionId = transactionId;
    }

    public LogEvent() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotNull LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(@NotNull LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public @NotNull EventType getType() {
        return type;
    }

    public void setType(@NotNull EventType type) {
        this.type = type;
    }

    public @Size(max = 1024) String getMessage() {
        return message;
    }

    public void setMessage(@Size(max = 1024) String message) {
        this.message = message;
    }

    public @Pattern(regexp = "^[a-zA-Z0-9]{1,6}$") String getUserId() {
        return userId;
    }

    public void setUserId(@Pattern(regexp = "^[a-zA-Z0-9]{1,6}$") String userId) {
        this.userId = userId;
    }

    public @NotNull UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(@NotNull UUID transactionId) {
        this.transactionId = transactionId;
    }
}
