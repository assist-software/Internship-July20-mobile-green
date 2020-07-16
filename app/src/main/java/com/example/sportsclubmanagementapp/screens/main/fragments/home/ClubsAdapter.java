package com.example.sportsclubmanagementapp.screens.main.fragments.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Clubs;

import java.util.List;

public class ClubsAdapter extends RecyclerView.Adapter<ClubsAdapter.ClubsViewHolder> {

    private List<Clubs> Clubss;
    private Context context;

    public ClubsAdapter(List<Clubs> Clubss, Context context) {
        this.Clubss = Clubss;
        this.context = context;
    }

    public class ClubsViewHolder extends RecyclerView.ViewHolder {
        private TextView club_name;

        public ClubsViewHolder(@NonNull View itemView) {
            super(itemView);
            club_name = itemView.findViewById(R.id.name);
        }

        public void bind(Clubs Clubs) {
            club_name.setText(Clubs.getName());
        }
    }

    @NonNull
    @Override
    public ClubsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_club_join, parent, false);
        return new ClubsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClubsViewHolder holder, int position) {
        holder.bind(Clubss.get(position));
    }

    @Override
    public int getItemCount() {
        return this.Clubss.size();
    }

    public Context getContext(){
        return this.context;
    }
}
