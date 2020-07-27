package com.example.sportsclubmanagementapp.screens.main.fragments.home;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Event;
import com.example.utils.Utils;

import java.util.List;
import java.util.Random;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    public static final int HORIZONTAL_BTN_EVENT = 1;
    public static final int HORIZONTAL_NO_BTN_EVENT = 2;
    public static final int VERTICAL_BTN_EVENT = 3;
    public static final int VERTICAL_NO_BTN_EVENT = 4;
    private List<Drawable> eventsPictures; //random event pictures for TESTS
    private List<Event> events;
    private Context context;
    private int typeLayout;
    private OnEventItemListener listener;

    public EventAdapter(List<Event> events, Context context, int typeLayout, OnEventItemListener listener) {
        this.events = events;
        this.context = context;
        this.typeLayout = typeLayout;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        eventsPictures = Utils.getEventsPictures(getContext()); //for TESTS
        View view;
        if (typeLayout == HORIZONTAL_NO_BTN_EVENT || typeLayout == HORIZONTAL_BTN_EVENT) { //for horizontal recycler events
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        } else { //for vertical recycler events
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_vertical, parent, false);
        }
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

    public void removeEvent(Event event) {
        Event eventToRemove = this.events.stream().filter(c -> c.getId() == event.getId()).findFirst().orElse(null);
        if (eventToRemove != null) {
            this.events.remove(event);
            notifyDataSetChanged();
        }
    }

    public Context getContext() {
        return this.context;
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
        private CardView eventLinearLayout;
        private TextView name;
        private TextView location;
        private TextView date;
        private Button join;
        private ImageView image;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            location = itemView.findViewById(R.id.location);
            date = itemView.findViewById(R.id.date);
            eventLinearLayout = itemView.findViewById(R.id.eventLinearLayout);
            image = itemView.findViewById(R.id.image);
            join = itemView.findViewById(R.id.join);

        }

        public void bind(final Event event) { /*type selects the layout (to avoid multiple layout)*/
            name.setText(event.getName());
            location.setText(event.getLocation());
            date.setText(event.getDate());
            image.setImageDrawable(eventsPictures.get(new Random().nextInt(5)));

            if (typeLayout == HORIZONTAL_NO_BTN_EVENT || typeLayout == VERTICAL_NO_BTN_EVENT) { /*hide the JOIN button for type 2 and 4*/
                join.setVisibility(View.INVISIBLE);
            } else {
                if (join != null) join.setOnClickListener(v -> listener.onEventsJoinClick(event));
            }

            eventLinearLayout.setOnClickListener(v -> listener.onEventsClick(event));
        }
    }
}
