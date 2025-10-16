package com.example.e_cynic.entity;

import androidx.annotation.Nullable;

import java.util.Arrays;

public class Item {
    public Integer itemId;
    public Integer orderId;
    public String itemName;
    public byte[] image;
    public Integer point;

    public Item() {
    }

    public Item(byte[] image) {
        this.image = image;
    }

    public Item(String itemName, @Nullable byte[] image) {
        this.itemName = itemName;
        this.image = image;
    }

    public Item(@Nullable Integer itemId, Integer orderId, String itemName, byte[] image,
                @Nullable Integer point) {
        this.itemId = itemId;
        this.orderId = orderId;
        this.itemName = itemName;
        this.image = image;
        this.point = point;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", orderId=" + orderId +
                ", itemName='" + itemName + '\'' +
                ", image=" + Arrays.toString(image) +
                ", point=" + point +
                '}';
    }
}
