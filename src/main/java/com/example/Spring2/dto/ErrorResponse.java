package com.example.Spring2.dto;

import lombok.Data;
@Data
public class ErrorResponse<T> {
    private String message;
    private T data;
    private Boolean success;

    public ErrorResponse() {
        this.message = "Error!";
        this.data = null;
        this.success = false;
    }
    public ErrorResponse(String msg) {
        this.message = msg;
        this.data = null;
        this.success = false;
    }
    public ErrorResponse(String msg, T data) {
        this.message = msg;
        this.data = data;
        this.success = false;
    }
}
