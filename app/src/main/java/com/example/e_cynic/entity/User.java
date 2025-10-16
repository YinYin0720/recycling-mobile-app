package com.example.e_cynic.entity;

import androidx.annotation.Nullable;

public class User {
    public Integer userId;
    public String username;
    public String email;
    public String password;
    public String phoneNumber;

    public User() {}

    public User(@Nullable Integer userId, String username, @Nullable String email,
                @Nullable String password, @Nullable String phoneNumber) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
