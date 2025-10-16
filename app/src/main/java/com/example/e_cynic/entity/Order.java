package com.example.e_cynic.entity;

import androidx.annotation.Nullable;

public class Order {
    public Integer orderId;
    public Integer userId;
    public Integer addressId;
    public Long date;
    public String status;

    public Order() {
    }

    public Order(@Nullable Integer orderId, @Nullable String status, Long date) {
        this.orderId = orderId;
        this.date = date;
        this.status = status;
    }

    public Order(@Nullable Integer orderId, Integer userId, Integer addressId, Long date, @Nullable String status) {
        this.orderId = orderId;
        this.userId = userId;
        this.addressId = addressId;
        this.date = date;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", addressId=" + addressId +
                ", date=" + date +
                ", status='" + status + '\'' +
                '}';
    }
}