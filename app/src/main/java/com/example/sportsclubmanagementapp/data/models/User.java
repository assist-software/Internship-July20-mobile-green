package com.example.sportsclubmanagementapp.data.models;

public class User {
    private String username;

    private long id;
    private String email;
    private String token;
    private String first_and_last_name;
    private String password;
    private String confirm_password;
    private Role role;
    private String primarySport;
    private String secondarySport;
    private double height;
    private double weight;
    private int age;

    public User(long id, String first_and_last_name, String email, String password, Role role, String primarySport, String secondarySport, double height, double weight, int age) {
        this.id = id;
        this.first_and_last_name = first_and_last_name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.primarySport = primarySport;
        this.secondarySport = secondarySport;
        this.height = height;
        this.weight = weight;
        this.age = age;
    }

    public User(String email, String first_and_last_name, String password, String confirm_password) { // for register activity
        this.email = email;
        this.first_and_last_name = first_and_last_name;
        this.password = password;
        this.confirm_password = confirm_password;
    }

    public User(String username, String password) {  // for login activity
        this.username = username;
        this.email = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getFirst_and_last_name() {
        return first_and_last_name;
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

    public String getToken() {
        return token;
    }
}
