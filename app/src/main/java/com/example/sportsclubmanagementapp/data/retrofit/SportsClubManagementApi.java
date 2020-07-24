package com.example.sportsclubmanagementapp.data.retrofit;

import com.example.sportsclubmanagementapp.data.models.Club;
import com.example.sportsclubmanagementapp.data.models.Event;
import com.example.sportsclubmanagementapp.data.models.Sport;
import com.example.sportsclubmanagementapp.data.models.User;
import com.example.sportsclubmanagementapp.data.models.UserAccountSetup;
import com.example.sportsclubmanagementapp.data.models.UserLogIn;
import com.example.sportsclubmanagementapp.data.models.UserRegister;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface SportsClubManagementApi {

    @GET("events/") //get all events
    Call <List<Event>> getEvents();

    @GET("users/") //get all users
    Call <List<User>> getUsers();

    @GET("sports/") //get all sports
    Call <List<Sport>> getSports();

    @GET("/api/clubs/") //get all clubs
    Call <List<Club>> getClubs();

    @GET("api/clubs/user/unjoined/")
    Call <List<Club>> getUnJoinedClubs(@Header ("Authorization") String token);

    @GET("api/clubs/user/joined/")
    Call <List<Club>> getJoinedClubs(@Header ("Authorization") String token);

    @GET("api/clubs/user/pending/")
    Call <List<Club>> getPendingClubs(@Header ("Authorization") String token);

    @POST("user/register/validate/")  //register activity
    Call <Void> createPostUserRegister(@Body UserRegister userRegister);

    @POST("user/signin/") //login activity
    Call <UserLogIn> createPostUserLogIn(@Body UserLogIn userLogIn);

    @POST("user/register/") //accountsetup activity
    Call <Void> createPostUserAccountSetup(@Body UserAccountSetup userAccountSetup);

    @POST("api/clubs/join/{id}/") //user join club
    Call <Void> createPostUserJoinClub(@Header ("Authorization") String token, @Path ("id") int id);

}
