package com.example.sportsclubmanagementapp.data.models;

public class Member {
    private int id;
    private String first_name;
    private String last_name;
    private int gender;
    private int age;
    private int primary_sport;
    private int secondary_sport;

    public Member(int id, String first_name, String last_name, int gender, int age, int primary_sport, int secondary_sport) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.age = age;
        this.primary_sport = primary_sport;
        this.secondary_sport = secondary_sport;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
