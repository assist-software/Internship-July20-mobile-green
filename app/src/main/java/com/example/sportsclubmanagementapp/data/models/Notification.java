package com.example.sportsclubmanagementapp.data.models;

public class Notification {

    private String time;
    private String role;
    private String name;
    private String action;
    private String actionOn;

    public Notification(String time, String role, String name, String action, String actionOn) {
        this.time = time;
        this.role = role;
        this.name = name;
        this.action = action;
        this.actionOn = actionOn;
    }

    public String getTime() {
        return time;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getAction() {
        return action;
    }

    public String getActionOn() {
        return actionOn;
    }
}
