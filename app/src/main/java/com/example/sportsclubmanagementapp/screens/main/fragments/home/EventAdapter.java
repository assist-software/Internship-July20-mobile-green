package com.example.sportsclubmanagementapp.screens.main.fragments.home;

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

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<Event> events;
    private Context context;
    private int typeLayout;

    public EventAdapter(List<Event> events, Context context, int typeLayout) {
        this.events = events;
        this.context = context;
        this.typeLayout = typeLayout;
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView location;
        private TextView date;
        private Button join;
        //private ImageView image;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            location = itemView.findViewById(R.id.location);
            date = itemView.findViewById(R.id.date);
            //image = itemView.findViewById(R.id.image);
            if(typeLayout == 2 || typeLayout == 4){
                join = itemView.findViewById(R.id.join);
            }
        }

        public void bind(Event event) { /*type selects the layout (to avoid multiple layout)*/
            name.setText(event.getName());
            location.setText(event.getLocation());
            date.setText(event.getDate());
            //image = ;
            if(typeLayout == 2 || typeLayout == 4){ /*hide the JOIN button for type 2 and 4*/
                join.setVisibility(View.INVISIBLE);
            }
        }
    }


    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(typeLayout == 3 || typeLayout == 4){ //for vertical recycler events
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_horizontal, parent, false);
        }
        else{ //for horizontal recycler events
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
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

    public Context getContext(){
        return this.context;
    }
}
