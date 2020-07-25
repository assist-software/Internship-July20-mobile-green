package com.example.sportsclubmanagementapp.screens.main.fragments.home;
import com.example.sportsclubmanagementapp.data.models.Event;

public interface OnEventItemListener {
    void onEventsClick(Event event);
    void onEventsJoinClick(Event event);
}
