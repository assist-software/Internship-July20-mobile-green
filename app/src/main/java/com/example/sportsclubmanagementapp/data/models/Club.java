package com.example.sportsclubmanagementapp.data.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Club implements Serializable { //hardcoded data & api
    private int id;
    @SerializedName("owner")
    private int ownerId;
    private String name;

    private String description;
    private long invites_id;
    private long requests_id;
    private long members_id;

    public Club(int id, int ownerId, String name, String description, long invites_id, long requests_id, long members_id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.invites_id = invites_id;
        this.requests_id = requests_id;
        this.members_id = members_id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
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
