package com.example.sportsclubmanagementapp.screens.calendar;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class EventChildAdapter extends RecyclerView.Adapter<EventChildAdapter.EventViewHolder> {

    private List<Drawable> eventsPictures; //random event pictures for TESTS

    private List<Event> events;
    private Activity activity;

    public EventChildAdapter(List<Event> events, Activity activity) {
        this.events = events;
        this.activity = activity;
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView location;
        private TextView date;
        private ImageView image;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            location = itemView.findViewById(R.id.location);
            date = itemView.findViewById(R.id.date);
            image = itemView.findViewById(R.id.image);
        }

        public void bind(Event event, List<Drawable> eventsPictures) {
            name.setText(event.getName());
            location.setText(event.getLocation());
            date.setText(event.getDate());
            image.setImageDrawable(eventsPictures.get(new Random().nextInt(5)));
        }
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_horizontal, parent, false);
        prepareEventsPictures(); //for TESTS
        return new EventViewHolder(view);
}

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
            holder.bind(events.get(position),eventsPictures);
    }

    @Override
    public int getItemCount() {
        return this.events.size();
    }

    public Context getContext(){
        return this.activity;
    }

    private void prepareEventsPictures() {
        eventsPictures = new ArrayList<>();
        eventsPictures.add(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.img_running));
        eventsPictures.add(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.img_biking));
        eventsPictures.add(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.img_tennis));
        eventsPictures.add(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.img_running_1));
        eventsPictures.add(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.img_motors));
    }
}
