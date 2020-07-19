package com.example.sportsclubmanagementapp.data.models;

import java.util.List;
import java.util.Objects;

public class Event {
    private long id;
    private long clubId;
    private String name;
    private String description;
    private String location;
    private String date;
    private int radius;
    private String sportType;
    private long invites_id;
    private long requests_id;
    private long members_id;


    public Event(long id, long clubId, String name, String description, String location, String date, int radius, String sportType, long invites_id, long requests_id, long members_id ) {
        this.id = id;
        this.clubId = clubId;
        this.name = name;
        this.description = description;
        this.location = location;
        this.date = date;
        this.radius = radius;
        this.sportType = sportType;
        this.invites_id = invites_id;
        this.requests_id = requests_id;
        this.members_id = members_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSportType() {
        return sportType;
    }

    public void setSportType(String sportType) {
        this.sportType = sportType;
    }

    public long getInvites_id() {
        return invites_id;
    }

    public void setInvites_id(long invites_id) {
        this.invites_id = invites_id;
    }

    public long getRequests_id() {
        return requests_id;
    }

    public void setRequests_id(long requests_id) {
        this.requests_id = requests_id;
    }

    public long getMembers_id() {
        return members_id;
    }

    public void setMembers_id(long members_id) {
        this.members_id = members_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getClubId() {
        return clubId;
    }

    public void setClubId(long clubId) {
        this.clubId = clubId;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
