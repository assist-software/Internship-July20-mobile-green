package com.example.sportsclubmanagementapp.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClubDetails {
    private String name;
    @SerializedName("owner")
    private Coach coach;
    private List<User> members;
    private List<Event> events;

    public ClubDetails(String name, Coach coach, List<User> members, List<Event> events) {
        this.name = name;
        this.coach = coach;
        this.members = members;
        this.events = events;
    }

    public String getName() {
        return name;
    }

    public Coach getCoach() {
        return coach;
    }

    public List<User> getMembers() {
        return members;
    }

    public List<Event> getEvents() {
        return events;
    }
}
