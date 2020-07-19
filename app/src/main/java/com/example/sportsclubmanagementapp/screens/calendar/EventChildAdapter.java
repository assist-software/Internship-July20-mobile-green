package com.example.sportsclubmanagementapp.screens.calendar;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Event;

import java.util.List;

public class EventChildAdapter extends RecyclerView.Adapter<EventChildAdapter.EventViewHolder> {

    private List<Event> events;
    private Activity activity;

    public EventChildAdapter(List<Event> events, Activity activity) {
        this.events = events;
        this.activity = activity;
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView location;
        private TextView date;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            location = itemView.findViewById(R.id.location);
            date = itemView.findViewById(R.id.date);
        }

        public void bind(Event event) {
            name.setText(event.getName());
            location.setText(event.getLocation());
            date.setText(event.getDate());
        }
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_horizontal, parent, false);
        //view.findViewById(R.id.join).setVisibility(View.GONE);
        return new EventViewHolder(view);
}

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
            holder.bind(events.get(position));
    }

    @Override
    public int getItemCount() {
        return this.events.size();
    }

    public Context getContext(){
        return this.activity;
    }
}
