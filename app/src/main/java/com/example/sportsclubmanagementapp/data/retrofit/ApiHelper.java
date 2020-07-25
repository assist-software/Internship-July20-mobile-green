package com.example.sportsclubmanagementapp.data.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHelper {
    private static SportsClubManagementApi sportsClubManagementApi = null;
    private static final String URL = "http://25.35.52.160:8001/";
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
