package com.example.sportsclubmanagementapp.screens.main.fragments.home;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Club;

import java.util.List;

public class ClubsAdapter extends RecyclerView.Adapter<ClubsAdapter.ClubsViewHolder> {

    public static final int JOIN_CLUB_LAYOUT = 1;
    public static final int JOINED_CLUB_LAYOUT = 2;
    public static final int PENDING_CLUB_LAYOUT = 3;

    private int layoutType;
    private List<Club> clubs;
    private Context context;
    private OnClubItemListener listener;

    public ClubsAdapter(List<Club> Clubs, Context context, int layoutType, OnClubItemListener listener) {
        this.clubs = Clubs;
        this.context = context;
        this.layoutType = layoutType;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ClubsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_club, parent, false);
        return new ClubsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClubsViewHolder holder, int position) {
        holder.bind(clubs.get(position));
    }

    @Override
    public int getItemCount() {
        if (this.clubs == null) return 0;
        return this.clubs.size();
    }

    public Context getContext() {
        return this.context;
    }

    public void addClub(Club club) {
        this.clubs.add(club);
        notifyDataSetChanged();
    }

    public void removeClub(Club club) {
        Club clubToRemove = this.clubs.stream().filter(c -> c.getId() == club.getId()).findFirst().orElse(null);
        if (clubToRemove != null) {
            this.clubs.remove(clubToRemove);
            notifyDataSetChanged();
        }
    }

    public class ClubsViewHolder extends RecyclerView.ViewHolder {
        private TextView club_name;
        private LinearLayout linearLayout;
        private Button join;
        private TextView textUnderButton;

        public ClubsViewHolder(@NonNull View itemView) {
            super(itemView);
            club_name = itemView.findViewById(R.id.name);
            linearLayout = itemView.findViewById(R.id.clubItemLayout);
            join = itemView.findViewById(R.id.join);
            textUnderButton = itemView.findViewById(R.id.textUnderButton);
        }

        public void bind(final Club club) {
            club_name.setText(club.getName());

            if (layoutType == JOIN_CLUB_LAYOUT) {
                join.setVisibility(View.VISIBLE);
                textUnderButton.setVisibility(View.GONE);
                join.setOnClickListener(v -> listener.onClubsJoinClick(club));
            } else if (layoutType == JOINED_CLUB_LAYOUT) {
                join.setVisibility(View.GONE);
                textUnderButton.setVisibility(View.VISIBLE);
                textUnderButton.setText(getContext().getResources().getText(R.string.joined_txt));
            } else if (layoutType == PENDING_CLUB_LAYOUT) {
                join.setVisibility(View.GONE);
                textUnderButton.setVisibility(View.VISIBLE);
                textUnderButton.setText(getContext().getResources().getText(R.string.pending_txt_caps));
            }
            linearLayout.setOnClickListener(v -> listener.onClubsClick(club));
        }
    }
}
