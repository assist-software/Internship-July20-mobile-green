package com.example.sportsclubmanagementapp.data.models;

import com.google.gson.annotations.SerializedName;

public class Workouts { //hardcoded data

    @SerializedName("owner")
    private long ownerId;
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("lat")
    private double latitude;
    @SerializedName("lng")
    private double longitude;
    private double radius;
    private double duration;
    @SerializedName("distance")
    private double distance;
    private double average_hr;
    private double calories_burned;
    private double average_speed;
    private boolean workout_effectiveness;

    private long id;
    private String sportType;
    private String location;

    public Workouts(long id, long ownerId, String name, String description, String sportType, String location, double radius,
                    double duration, double distance, double average_hr, double calories_burned, double average_speed, boolean workout_effectiveness) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.description = description;
        this.sportType = sportType;
        this.location = location;
        this.radius = radius;
        this.duration = duration;
        this.distance = distance;
        this.average_hr = average_hr;
        this.calories_burned = calories_burned;
        this.average_speed = average_speed;
        this.workout_effectiveness = workout_effectiveness;
    }

    public Workouts(long ownerId, String name, String description, double latitude, double longitude, double radius, double duration, double distance, double average_hr,
                    double calories_burned, double average_speed, boolean workout_effectiveness) {  //API
        this.ownerId = ownerId;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.duration = duration;
        this.distance = distance;
        this.average_hr = average_hr;
        this.calories_burned = calories_burned;
        this.average_speed = average_speed;
        this.workout_effectiveness = workout_effectiveness;

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

    public String getSportType() {
        return sportType;
    }

    public void setSportType(String sportType) {
        this.sportType = sportType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getLatitude() { return latitude; }

    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }

    public void setLongitude(double longitude) { this.longitude = longitude; }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) { this.duration = duration; }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getAverage_hr() {
        return average_hr;
    }

    public void setAverage_hr(double average_hr) {
        this.average_hr = average_hr;
    }

    public double getCalories_burned() {
        return calories_burned;
    }

    public void setCalories_burned(double calories_burned) { this.calories_burned = calories_burned; }

    public double getAverage_speed() {
        return average_speed;
    }

    public void setAverage_speed(double average_speed) {
        this.average_speed = average_speed;
    }

    public boolean isWorkout_effectiveness() {
        return workout_effectiveness;
    }

    public void setWorkout_effectiveness(boolean workout_effectiveness) { this.workout_effectiveness = workout_effectiveness; }
}
