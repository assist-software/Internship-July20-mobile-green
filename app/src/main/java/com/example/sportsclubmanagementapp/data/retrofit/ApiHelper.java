package com.example.sportsclubmanagementapp.data.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHelper {
    private static final String URL = "http://192.168.149.51:8001";
    private static SportsClubManagementApi sportsClubManagementApi = null;

    public static SportsClubManagementApi getApi() {
        if (sportsClubManagementApi == null) {
            Retrofit retrofit;
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            sportsClubManagementApi = retrofit.create(SportsClubManagementApi.class);
        }
        return sportsClubManagementApi;
    }
}
