package com.example.sportsclubmanagementapp.data.retrofit;

import com.example.sportsclubmanagementapp.data.models.Clubs;
import com.example.sportsclubmanagementapp.data.models.Event;
import com.example.sportsclubmanagementapp.data.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface SportsClubManagementApi {


    @GET("clubs")  //get all clubs
    Call <List<Clubs>> getClubs();

    @GET("events") //get all events
    Call <List<Event>> getEvents();

    @GET("users") //get all users
    Call <List<User>> getUsers();

    @POST("user/register/")  //register activity
    Call <User> createPostUserRegister(@Body User userRegister);

    @POST("user/signin/") //login activity
    Call <User> createPostUserLogIn(@Body User userLogIn);

}
