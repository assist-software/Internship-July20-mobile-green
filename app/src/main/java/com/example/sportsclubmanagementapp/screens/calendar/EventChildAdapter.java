package com.example.sportsclubmanagementapp.screens.calendar;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Event;
import com.example.sportsclubmanagementapp.screens.main.fragments.home.OnEventItemListener;
import com.example.utils.Utils;

import java.util.List;
import java.util.Random;

public class EventChildAdapter extends RecyclerView.Adapter<EventChildAdapter.EventViewHolder> {

    private List<Event> events;
    private Activity activity;
    private OnEventItemListener listener;

    public EventChildAdapter(List<Event> events, Activity activity, OnEventItemListener listener) {
        this.events = events;
        this.activity = activity;
        this.listener = listener;
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        CardView body;
        private TextView name;
        private TextView location;
        private TextView date;
        private ImageView image;
        private Button join;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            body = itemView.findViewById(R.id.eventLinearLayout);
            name = itemView.findViewById(R.id.name);
            location = itemView.findViewById(R.id.location);
            date = itemView.findViewById(R.id.date);
            image = itemView.findViewById(R.id.image);
            join = itemView.findViewById(R.id.join);
        }

        public void bind(Event event, Context context, OnEventItemListener listener) {
            name.setText(event.getName());
            location.setText(event.getLocation());
            date.setText(event.getDate());
            image.setImageDrawable(Utils.getEventsPictures(context).get(new Random().nextInt(5)));
            join.setVisibility(View.GONE);
            body.setOnClickListener(v -> listener.onEventsClick(event));
        }
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_vertical, parent, false);
        return new EventViewHolder(view);
}

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
            holder.bind(events.get(position), getContext(), listener);
    }

    @Override
    public int getItemCount() {
        return this.events.size();
    }

    public Context getContext(){
        return this.activity;
    }
}
