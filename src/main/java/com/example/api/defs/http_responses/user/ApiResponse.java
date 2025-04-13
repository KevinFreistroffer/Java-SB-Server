package com.example.api.defs.http_responses.user;

import org.springframework.http.HttpStatus;

public class ApiResponse<T> {
    private T data;
    private String message;
    private int status;

    public ApiResponse(T data, String message, int status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }

    // Getters and setters
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    // Static factory methods for common responses
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data, "Success", HttpStatus.OK.value());
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(data, message, HttpStatus.OK.value());
    }

    public static <T> ApiResponse<T> error(String message, int status) {
        return new ApiResponse<>(null, message, status);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(null, message, HttpStatus.BAD_REQUEST.value());
    }
} 