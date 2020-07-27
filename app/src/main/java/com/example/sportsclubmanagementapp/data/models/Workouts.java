package com.example.sportsclubmanagementapp.data.models;

public class Workouts { //api data
    private int event;
    private String name;
    private String description;
    private double lat;
    private double lng;
    private double radius;
    private double duration;
    private double distance;
    private double bpm;
    private double calories_burned;
    private double average_speed;
    private String workout_effectiveness;
    private String date;
    private String time;

    public Workouts(int event, String name, String description, double lat, double lng, double radius, double duration, double distance, double bpm, double calories_burned, double average_speed, String workout_effectiveness, String date, String time) {
        this.event = event;
        this.name = name;
        this.description = description;
        this.lat = lat;
        this.lng = lng;
        this.radius = radius;
        this.duration = duration;
        this.distance = distance;
        this.bpm = bpm;
        this.calories_burned = calories_burned;
        this.average_speed = average_speed;
        this.workout_effectiveness = workout_effectiveness;
        this.date = date;
        this.time = time;
    }


    public Workouts(int event, double duration, double distance, double bpm, double calories_burned, double average_speed, String workout_effectiveness) { //
        this.event = event;
        this.duration = duration;
        this.distance = distance;
        this.bpm = bpm;
        this.calories_burned = calories_burned;
        this.average_speed = average_speed;
        this.workout_effectiveness = workout_effectiveness;
    }

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
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

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getBpm() {
        return bpm;
    }

    public void setBpm(double bpm) {
        this.bpm = bpm;
    }

    public double getCalories_burned() {
        return calories_burned;
    }

    public void setCalories_burned(double calories_burned) {
        this.calories_burned = calories_burned;
    }

    public double getAverage_speed() {
        return average_speed;
    }

    public void setAverage_speed(double average_speed) {
        this.average_speed = average_speed;
    }

    public String getWorkout_effectiveness() {
        return workout_effectiveness;
    }

    public void setWorkout_effectiveness(String workout_effectiveness) {
        this.workout_effectiveness = workout_effectiveness;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
