package com.bookstore.orderservice.dto;

public class UpdateOrderStatusRequest {

    private String status;

    public UpdateOrderStatusRequest() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

