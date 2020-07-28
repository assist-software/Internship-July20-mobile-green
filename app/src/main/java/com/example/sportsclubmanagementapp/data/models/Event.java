package com.example.sportsclubmanagementapp.data.models;

public class Event {
    private int id;
    private String name;
    private String description1;
    private String description2;
    private String date;
    private String time;
    private String location;
    private int[] status;

    public Event(int id, String name, String description1, String description2, String date, String time, String location, int[] status) {
        this.id = id;
        this.name = name;
        this.description1 = description1;
        this.description2 = description2;
        this.date = date;
        this.time = time;
        this.location = location;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int[] getStatus() {
        return status;
    }

    public void setStatus(int[] status) {
        this.status = status;
    }

    public String getDescription1() {
        return description1;
    }

    public void setDescription1(String description1) {
        this.description1 = description1;
    }

    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }
}
