package com.example.sportsclubmanagementapp.data.models;

public class SportType {

    private boolean Running;
    private boolean Clicling;
    private boolean TeamSports;
    private boolean WeightLifting;

    public SportType(boolean running, boolean clicling, boolean teamSports, boolean weightLifting) {
        Running = running;
        Clicling = clicling;
        TeamSports = teamSports;
        WeightLifting = weightLifting;
    }

    public boolean isRunning() {
        return Running;
    }

    public boolean isClicling() {
        return Clicling;
    }

    public boolean isTeamSports() {
        return TeamSports;
    }

    public boolean isWeightLifting() {
        return WeightLifting;
    }
}
