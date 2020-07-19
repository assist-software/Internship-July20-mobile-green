package com.example.sportsclubmanagementapp.screens.calendar;

import android.app.Activity;
import android.content.Context;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Clubs;
import com.example.sportsclubmanagementapp.data.models.Event;

import java.util.ArrayList;
import java.util.List;

public class EventParentAdapter extends RecyclerView.Adapter<EventParentAdapter.ClubViewHolder> {

    private List<Clubs> clubList;
    private List<Event> eventList;
    private Activity activity;

    public EventParentAdapter(List<Clubs> clubList, List<Event> eventList, Activity activity) {
        this.clubList = clubList;
        this.activity = activity;
        this.eventList = eventList;
    }

    public class ClubViewHolder extends RecyclerView.ViewHolder {
        private TextView club_name;
        RecyclerView club_events;

        public ClubViewHolder(@NonNull View itemView) {
            super(itemView);
            club_name = itemView.findViewById(R.id.club_name);
            club_events = itemView.findViewById(R.id.events_recycler_view);
        }

        public void bind(Clubs club) {
            club_name.setText(club.getName());
        }
    }

    @NonNull
    @Override
    public ClubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_club_events, parent, false);
        return new ClubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClubViewHolder holder, int position) {
        if (clubList.isEmpty() || eventList.isEmpty()) return;

        holder.bind(clubList.get(position));

        EventChildAdapter eventsAdapter = new EventChildAdapter(eventList, activity);
        LinearLayoutManager eventsLayoutManager = new LinearLayoutManager(activity);
        holder.club_events.setLayoutManager(eventsLayoutManager);
        holder.club_events.setAdapter(eventsAdapter);
        eventsAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.clubList.size();
    }

    public Context getActivity() {
        return this.activity;
    }
}
