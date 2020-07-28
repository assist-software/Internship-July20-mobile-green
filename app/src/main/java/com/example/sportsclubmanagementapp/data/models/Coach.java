package com.example.sportsclubmanagementapp.data.models;

public class Coach {
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private int age;
    private String[] clubs;

    public int getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public String[] getClubs() {
        return clubs;
    }

    public Coach(int id, String first_name, String last_name, String email, int age, String[] clubs) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.age = age;
        this.clubs = clubs;
    }
}
