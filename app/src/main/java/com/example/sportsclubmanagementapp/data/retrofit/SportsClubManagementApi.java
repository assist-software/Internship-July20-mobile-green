package com.example.sportsclubmanagementapp.data.retrofit;

import com.example.sportsclubmanagementapp.data.models.Club;
import com.example.sportsclubmanagementapp.data.models.ClubDetails;
import com.example.sportsclubmanagementapp.data.models.Coach;
import com.example.sportsclubmanagementapp.data.models.Event;
import com.example.sportsclubmanagementapp.data.models.EventDetails;
import com.example.sportsclubmanagementapp.data.models.Sport;
import com.example.sportsclubmanagementapp.data.models.UserAccountSetup;
import com.example.sportsclubmanagementapp.data.models.UserLogIn;
import com.example.sportsclubmanagementapp.data.models.UserRegister;
import com.example.sportsclubmanagementapp.data.models.Workout;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SportsClubManagementApi {

    @GET("/api/events/user/")
        //get all events
    Call<List<Event>> getEvents(@Header("Authorization") String token);

    @GET("/api/workouts/")
        //get all user workouts
    Call<List<Workout>> getWorkouts(@Header("Authorization") String token);

    @GET("sports/")
        //get all sports
    Call<List<Sport>> getSports();

    @GET("/api/clubs/")
        //get all clubs
    Call<List<Club>> getClubs(@Header("Authorization") String token);

    @GET("api/clubs/user/unjoined/")
        //get all unjoined clubs by the user
    Call<List<Club>> getUnJoinedClubs(@Header("Authorization") String token);

    @GET("api/clubs/user/joined/")
        //get all joined clubs by the user
    Call<List<Club>> getJoinedClubs(@Header("Authorization") String token);

    @GET("api/clubs/user/pending/")
        //get all pending clubs by the user
    Call<List<Club>> getPendingClubs(@Header("Authorization") String token);

    @GET("api/clubs/{club_id}/")
    Call<ClubDetails> getClubDetails(@Header("Authorization") String token, @Path("club_id") int club_id);

    @GET("user/update/profile/")
        //get all data for the user
    Call<UserAccountSetup> getUserData(@Header("Authorization") String token);

    @GET("/api/events/{id}/")
        //get event details
    Call<EventDetails> getEventDetails(@Header("Authorization") String token, @Path("id") int id);


    @POST("user/register/validate/")
        //register activity
    Call<Void> createPostUserRegister(@Body UserRegister userRegister);

    @POST("user/signin/")
        //login activity
    Call<UserLogIn> createPostUserLogIn(@Body UserLogIn userLogIn);

    @POST("user/register/")
        //account setup activity
    Call<Void> createPostUserAccountSetup(@Body UserAccountSetup userAccountSetup);

    @POST("/user/reset-password/")
        //forgot password activity
    Call<Void> createPostEmail(@Body Map<String, String> email);

    @POST("api/clubs/join/{id}/")
        //user join club
    Call<Void> createPostUserJoinClub(@Header("Authorization") String token, @Path("id") int id);

    @POST("api/events/join/{id}/")
        //user join event
    Call<Void> createPostUserJoinEvent(@Header("Authorization") String token, @Path("id") int id);

    @POST("api/workouts/")
        //user add workout
    Call<Void> createPostWorkout(@Header("Authorization") String token, @Body Workout workout);


    @PUT("user/update/profile/")
        //user update his profile
    Call<Void> updateUserProfile(@Header("Authorization") String token, @Body UserAccountSetup userAccountSetup);
}
