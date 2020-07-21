package com.example.sportsclubmanagementapp.data.models;

import java.io.Serializable;
import java.util.Objects;

public class Clubs implements Serializable {
    private long id;
    private long ownerId;
    private String name;
    private String description;
    private long invites_id;
    private long requests_id;
    private long members_id;

    public Clubs(long id, long ownerId, String name, String description, long invites_id, long requests_id, long members_id ) {
        this.id = id;
        this.name = name + " Club";
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
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
