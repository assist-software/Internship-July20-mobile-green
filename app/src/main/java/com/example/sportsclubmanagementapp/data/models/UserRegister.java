package com.example.sportsclubmanagementapp.data.models;

public class UserRegister {
    private String email;
    private String first_and_last_name;
    private String password;
    private String confirm_password;

    public UserRegister(String email, String first_and_last_name, String password, String confirm_password) { // for register activity
        this.email = email;
        this.first_and_last_name = first_and_last_name;
        this.password = password;
        this.confirm_password = confirm_password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_and_last_name() {
        return first_and_last_name;
    }

    public void setFirst_and_last_name(String first_and_last_name) {
        this.first_and_last_name = first_and_last_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }
}
