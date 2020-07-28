package com.example.sportsclubmanagementapp.data.models;

import java.util.List;

public class EventDetails {
    private Event data;
    private List<User> members;

    public EventDetails(Event data, List<User> members) {
        this.data = data;
        this.members = members;
    }

    public Event getData() {
        return data;
    }

    public void setData(Event data) {
        this.data = data;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }
}
