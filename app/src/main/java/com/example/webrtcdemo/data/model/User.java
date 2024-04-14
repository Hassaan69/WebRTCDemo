package com.example.webrtcdemo.data.model;

import androidx.annotation.Nullable;

public class User {
    private String username;
    private String status;

    public User(String username, String status) {
        this.username = username;
        this.status = status;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }
}
