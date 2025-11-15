package com.example.dars;

import java.io.Serializable;

public class Report implements Serializable {
    private String disasterType;
    private String location;
    private String message;
    private String sender;
    private String timestamp;

    public Report(String disasterType, String location, String message, String sender, String timestamp) {
        this.disasterType = disasterType;
        this.location = location;
        this.message = message;
        this.sender = sender;
        this.timestamp = timestamp;
    }

    public String getDisasterType() {
        return disasterType;
    }

    public String getLocation() {
        return location;
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "[" + disasterType + "] " + location + "\nFrom: " + sender + "\n" + message + "\n" + timestamp;
    }
}