package com.example.sportsclubmanagementapp.screens.calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Club;
import com.example.sportsclubmanagementapp.data.models.Event;
import com.example.sportsclubmanagementapp.screens.eventdetails.EventDetailsActivity;
import com.example.sportsclubmanagementapp.screens.main.fragments.home.OnEventItemListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EventParentAdapter extends RecyclerView.Adapter<EventParentAdapter.ClubViewHolder> implements OnEventItemListener {

    private List<Club> clubList;
    private List<List<Event>> eventList;
    private Activity activity;
    LinearLayout item_club_events;

    public EventParentAdapter(List<Club> clubList, List<List<Event>> eventList, Activity activity) {
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

        public void bind(Club club) {
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
        EventChildAdapter eventsAdapter = new EventChildAdapter(eventList.get(position), activity, this);
        LinearLayoutManager eventsLayoutManager = new LinearLayoutManager(activity);
        holder.club_events.setLayoutManager(eventsLayoutManager);
        holder.club_events.setAdapter(eventsAdapter);
        eventsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onEventsClick(Event event) {
        Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
        intent.putExtra(getActivity().getResources().getString(R.string.event_id), event.getId());
        intent.putExtra(getActivity().getResources().getString(R.string.event_status), event.getStatus());
        getActivity().startActivity(intent);
    }

    @Override
    public void onEventsJoinClick(Event event) {

    }

    @Override
    public int getItemCount() {
        return this.clubList.size();
    }

    public Context getActivity() {
        return this.activity;
    }
}
