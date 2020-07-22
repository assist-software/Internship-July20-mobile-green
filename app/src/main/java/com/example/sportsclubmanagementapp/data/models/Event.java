package com.example.sportsclubmanagementapp.data.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Event implements Serializable {

    @SerializedName("club")
    private long clubId;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("location")
    private String location;
    @SerializedName("radius")
    private String radius;
    @SerializedName("sport")
    private String sportType;

    private long id;       
    private String date;
    private long invites_id;
    private long requests_id;
    private long members_id;


    public Event(long id, long clubId, String name, String description, String location, String date, String radius, String sportType, long invites_id, long requests_id, long members_id ) {
        this.id=id;
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

    public Event(long clubId, String name, String description, String location, String radius, String sportType){
        this.clubId=clubId;
        this.name=name;
        this.description=description;
        this.location=location;
        this.radius=radius;
        this.sportType=sportType;
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

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
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
