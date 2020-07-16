package com.example.sportsclubmanagementapp.screens.main.fragments.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Event;
import com.example.sportsclubmanagementapp.data.models.FutureEvents;

import java.util.List;
import java.util.concurrent.Future;

public class FutureEventsAdapter extends RecyclerView.Adapter<FutureEventsAdapter.FutureEventsViewHolder> {

    private List<FutureEvents> futureEvents;
    private Context context;

    public FutureEventsAdapter(List<FutureEvents> futureEvents, Context context) {
        this.futureEvents = futureEvents;
        this.context = context;
    }

    public class FutureEventsViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView location;
        private TextView date;
        //private ImageView image;

        public FutureEventsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            location = itemView.findViewById(R.id.location);
            date = itemView.findViewById(R.id.date);
            //image = itemView.findViewById(R.id.image);

        }

        public void bind(FutureEvents futureEvents) {
            name.setText(futureEvents.getName());
            location.setText(futureEvents.getLocation());
            date.setText(futureEvents.getDate());
            //image = ;
        }
    }

    @NonNull
    @Override
    public FutureEventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_future_event, parent, false);
        return new FutureEventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FutureEventsViewHolder holder, int position) {
            holder.bind(futureEvents.get(position));
    }

    @Override
    public int getItemCount() {
        return this.futureEvents.size();
    }

    public Context getContext(){
        return this.context;
    }
}
