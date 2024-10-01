package com.example.logeventservice.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.UUID;

import java.time.LocalDateTime;

public class LogEvent {

    private int id;

    @NotNull
    private LocalDateTime timestamp;

    @NotNull
    private EventType type;

    @Size(max = 1024)
    private String message;

    @Pattern(regexp = "^[a-zA-Z0-9]{1,6}$")
    private String userId;

    @NotNull
    private UUID transactionId;

    public LogEvent(int id, LocalDateTime timestamp, EventType type, String message, String userId, UUID transactionId) {
        this.id = id;
        this.timestamp = timestamp;
        this.type = type;
        this.message = message;
        this.userId = userId;
        this.transactionId = transactionId;
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
