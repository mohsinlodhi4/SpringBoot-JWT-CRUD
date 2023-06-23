package com.example.Spring2.dto;

import lombok.Data;
@Data
public class SuccessResponse<T> {
    private String message;
    private T data;
    private Boolean success;

    public SuccessResponse() {
        this.message = "Success!";
        this.data = null;
        this.success = true;
    }

    public SuccessResponse(String msg) {
        this.message = msg;
        this.data = null;
        this.success = true;
    }
    public SuccessResponse(String msg, T data) {
        this.message = msg;
        this.data = data;
        this.success = true;
    }
}
