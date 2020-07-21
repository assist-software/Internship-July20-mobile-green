package com.example.sportsclubmanagementapp.screens.main.fragments.home;

import com.example.sportsclubmanagementapp.data.models.Clubs;

public interface OnClubItemListener {
    void onClubsClick(Clubs club);
    void onClubsJoinClick(Clubs club);
}
