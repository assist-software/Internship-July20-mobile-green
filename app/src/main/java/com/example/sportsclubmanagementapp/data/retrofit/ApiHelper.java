package com.example.sportsclubmanagementapp.data.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHelper {

    private static SportsClubManagementApi sportsClubManagementApi = null;

    public static SportsClubManagementApi getApi(){
        if (sportsClubManagementApi == null) {
            Retrofit retrofit;
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://my-json-server.typicode.com/IonutDoroftei/FakeServer/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            sportsClubManagementApi = retrofit.create(SportsClubManagementApi.class);
        }
        return sportsClubManagementApi;
    }
}
