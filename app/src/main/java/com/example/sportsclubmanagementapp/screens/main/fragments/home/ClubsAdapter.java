package com.example.sportsclubmanagementapp.screens.main.fragments.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Clubs;

import java.util.List;

public class ClubsAdapter extends RecyclerView.Adapter<ClubsAdapter.ClubsViewHolder> {

    int layout;
    private List<Clubs> Clubs;
    private Context context;
    private OnClubItemListener listener;

    public ClubsAdapter(List<Clubs> Clubs, Context context, int layout, OnClubItemListener listener) {
        this.Clubs = Clubs;
        this.context = context;
        this.layout = layout;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ClubsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ClubsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClubsViewHolder holder, int position) {
        holder.bind(Clubs.get(position));
    }

    @Override
    public int getItemCount() {
        return this.Clubs.size();
    }

    public Context getContext() {
        return this.context;
    }

    public class ClubsViewHolder extends RecyclerView.ViewHolder {
        private TextView club_name;
        private LinearLayout linearLayout;

        public ClubsViewHolder(@NonNull View itemView) {
            super(itemView);
            club_name = itemView.findViewById(R.id.name);
            linearLayout = itemView.findViewById(R.id.clubItemLayout);
        }

        public void bind(final Clubs clubs) {
            club_name.setText(clubs.getName());
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClubsClick(clubs);
                }
            });
        }
    }
}
