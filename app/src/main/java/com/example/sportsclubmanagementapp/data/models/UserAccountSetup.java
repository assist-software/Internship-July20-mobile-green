package com.example.sportsclubmanagementapp.data.models;

public class UserAccountSetup { ////api data
    private String email;
    private String first_name;
    private String last_name;
    private int gender;
    private int role;
    private int age;
    private String password;
    private double height;
    private double weight;
    private int primary_sport;
    private int secondary_sport;

    public UserAccountSetup(String email, String first_name, String last_name, int gender, int role, int age, String password, double height, double weight, int primary_sport, int secondary_sport) {
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.role = role;
        this.age = age;
        this.password = password;
        this.height = height;
        this.weight = weight;
        this.primary_sport = primary_sport;
        this.secondary_sport = secondary_sport;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getPrimary_sport() {
        return primary_sport;
    }

    public void setPrimary_sport(int primary_sport) {
        this.primary_sport = primary_sport;
    }

    public int getSecondary_sport() {
        return secondary_sport;
    }

    public void setSecondary_sport(int secondary_sport) {
        this.secondary_sport = secondary_sport;
    }
}
