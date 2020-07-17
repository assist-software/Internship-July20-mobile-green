package com.example.sportsclubmanagementapp.data.models;

public class User {
    private long id;
    private String name;
    private String email;
    private String password;
    private Role role;
    private String primarySport;
    private String secondarySport;
    private double height;
    private double weight;
    private int age;

    public User(long id, String name, String email, String password, Role role, String primarySport, String secondarySport, double height, double weight, int age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.primarySport = primarySport;
        this.secondarySport = secondarySport;
        this.height = height;
        this.weight = weight;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public String getPrimarySport() {
        return primarySport;
    }

    public String getSecondarySport() {
        return secondarySport;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public int getAge() {
        return age;
    }
}
