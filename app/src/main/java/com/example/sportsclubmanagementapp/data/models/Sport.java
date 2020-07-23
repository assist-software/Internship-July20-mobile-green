package com.example.sportsclubmanagementapp.data.models;

import com.google.gson.annotations.SerializedName;

public class Sport {
    private int id;
    @SerializedName("type")
    private String sportName;

    public Sport(int id, String sportName) {
        this.id = id;
        this.sportName = sportName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }
}
