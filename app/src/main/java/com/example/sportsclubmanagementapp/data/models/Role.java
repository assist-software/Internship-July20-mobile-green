package com.example.sportsclubmanagementapp.data.models;

import java.util.Objects;

public class Role {
    private boolean admin;
    private boolean coach;
    private boolean athlete;

    public Role(boolean admin, boolean coach, boolean athlete) {
        this.admin = admin;
        this.coach = coach;
        this.athlete = athlete;
    }

    public boolean isAdmin() {
        return admin;
    }

    public boolean isCoach() {
        return coach;
    }

    public boolean isAthlete() {
        return athlete;
    }
}
