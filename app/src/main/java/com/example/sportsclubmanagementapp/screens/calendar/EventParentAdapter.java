package com.example.sportsclubmanagementapp.screens.calendar;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Clubs;
import com.example.sportsclubmanagementapp.data.models.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EventParentAdapter extends RecyclerView.Adapter<EventParentAdapter.ClubViewHolder> {

    private List<Clubs> clubList;
    private List<List<Event>> eventList;
    private Activity activity;
    LinearLayout item_club_events;

    public EventParentAdapter(List<Clubs> clubList, List<List<Event>> eventList, Activity activity) {
        this.clubList = clubList;
        this.activity = activity;
        this.eventList = eventList;
    }

    public class ClubViewHolder extends RecyclerView.ViewHolder {
        private TextView club_name;
        private RecyclerView club_events;

        public ClubViewHolder(@NonNull View itemView) {
            super(itemView);
            club_name = itemView.findViewById(R.id.club_name);
            club_events = itemView.findViewById(R.id.events_recycler_view);
            item_club_events = itemView.findViewById(R.id.club_events_recycler_view_layout);
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
        holder.bind(clubList.get(position));

        //for nested recycler view
        EventChildAdapter eventsAdapter = new EventChildAdapter(eventList.get(position), activity);
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
