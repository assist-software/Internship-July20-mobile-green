package com.example.sportsclubmanagementapp.data.retrofit;

public class JSONResponse {

    private AppData[] appData;

    public JSONResponse(AppData[] appData) {
        this.appData = appData;
    }

    public AppData[] getAppData() {
        return appData;
    }
}
