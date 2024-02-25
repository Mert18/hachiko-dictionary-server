package com.m2t.hachikodictionary.dto;

import java.time.LocalDateTime;

public class Response {
    private LocalDateTime timestamp;
    private Boolean success;
    private String message;
    private Object data;
    private Boolean showNotification;

    public Response(Boolean success, String message) {
        this(LocalDateTime.now(), success, message, null, true);
    }

    public Response(Boolean success, String message, Boolean showNotification) {
        this(LocalDateTime.now(), success, message, null, showNotification);
    }

    public Response(Boolean success, String message, Object data) {
        this(LocalDateTime.now(), success, message, data, true);
    }

    public Response(Boolean success, String message, Object data, Boolean showNotification) {
        this(LocalDateTime.now(), success, message, data, showNotification);
    }

    private Response(LocalDateTime timestamp, Boolean success, String message, Object data, Boolean showNotification) {
        this.timestamp = timestamp;
        this.success = success;
        this.message = message;
        this.data = data;
        this.showNotification = showNotification;
    }

    // Getters
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public Boolean getShowNotification() {
        return showNotification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Response response = (Response) o;

        if (!success.equals(response.success)) return false;
        return message.equals(response.message);
    }

    @Override
    public int hashCode() {
        int result = success.hashCode();
        result = 31 * result + message.hashCode();
        return result;
    }
}