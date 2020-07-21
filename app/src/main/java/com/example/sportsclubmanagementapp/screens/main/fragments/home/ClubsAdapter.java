package com.example.sportsclubmanagementapp.screens.main.fragments.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Clubs;

import org.w3c.dom.Text;

import java.util.List;

public class ClubsAdapter extends RecyclerView.Adapter<ClubsAdapter.ClubsViewHolder> {

    int layoutType;
    private List<Clubs> Clubs;
    private Context context;
    private OnClubItemListener listener;

    public ClubsAdapter(List<Clubs> Clubs, Context context, int layoutType, OnClubItemListener listener) {
        this.Clubs = Clubs;
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
        private Button join;
        private TextView textUnderButton;

        public ClubsViewHolder(@NonNull View itemView) {
            super(itemView);
            club_name = itemView.findViewById(R.id.name);
            linearLayout = itemView.findViewById(R.id.clubItemLayout);
            join = itemView.findViewById(R.id.join);
            textUnderButton = itemView.findViewById(R.id.textUnderButton);
        }

        public void bind(final Clubs club) {
            club_name.setText(club.getName());

            if(layoutType == 1){
                join.setVisibility(View.VISIBLE);
                textUnderButton.setVisibility(View.GONE);
                join.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onClubsJoinClick(club);
                    }
                });
            }
            else if (layoutType == 2){
                join.setVisibility(View.GONE);
                textUnderButton.setVisibility(View.VISIBLE);
                textUnderButton.setText(getContext().getResources().getText(R.string.joined_txt));
            }
            else if(layoutType == 3){
                join.setVisibility(View.GONE);
                textUnderButton.setVisibility(View.VISIBLE);
                textUnderButton.setText(getContext().getResources().getText(R.string.pending_txt_caps));
            }

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClubsClick(club);
                }
            });
        }
    }
}
