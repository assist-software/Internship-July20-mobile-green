package com.example.sportsclubmanagementapp.screens.main.fragments.clubs;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Clubs;
import com.example.sportsclubmanagementapp.screens.main.fragments.home.ClubsAdapter;
import com.example.sportsclubmanagementapp.screens.main.fragments.home.OnClubItemListener;
import com.example.sportsclubmanagementapp.screens.myprofile.MyProfileActivity;

import java.util.ArrayList;
import java.util.List;

public class ClubsFragment extends Fragment implements OnClubItemListener {

    //for new clubs recycler
    private List<Clubs> clubsList = new ArrayList<>();
    private RecyclerView recyclerViewClubs;
    private ClubsAdapter clubsAdapter;

    //for joined clubs recycler
    private List<Clubs> joinedClubsList = new ArrayList<>();
    private RecyclerView recyclerViewJoinedClubs;
    private ClubsAdapter joinedClubsAdapter;

    //for pending clubs recycler
    private List<Clubs> pendingClubsList = new ArrayList<>();
    private RecyclerView recyclerViewPendingClubs;
    private ClubsAdapter pendingClubsAdapter;

    public static ClubsFragment newInstance() {
        return new ClubsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setToolbar();
        return inflater.inflate(R.layout.fragment_clubs, container, false);
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Clubs");
        toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_default_avatar, null));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        //for new events recycler
        recyclerViewClubs = (RecyclerView) view.findViewById(R.id.new_clubs_recycler_view);
        clubsAdapter = new ClubsAdapter(clubsList, getContext(), R.layout.item_club_join, this);
        RecyclerView.LayoutManager clubsLayoutManager = new LinearLayoutManager(clubsAdapter.getContext());
        recyclerViewClubs.setLayoutManager(clubsLayoutManager);
        recyclerViewClubs.setAdapter(clubsAdapter);

        //for joined events recycler
        recyclerViewJoinedClubs = (RecyclerView) view.findViewById(R.id.joined_clubs_recycler_view);
        joinedClubsAdapter = new ClubsAdapter(joinedClubsList, getContext(), R.layout.item_club_joined, this);
        RecyclerView.LayoutManager joinedClubsLayoutManager = new LinearLayoutManager(joinedClubsAdapter.getContext());
        recyclerViewJoinedClubs.setLayoutManager(joinedClubsLayoutManager);
        recyclerViewJoinedClubs.setAdapter(joinedClubsAdapter);

        //for pending events recycler
        recyclerViewPendingClubs = (RecyclerView) view.findViewById(R.id.pending_clubs_recycler_view);
        pendingClubsAdapter = new ClubsAdapter(pendingClubsList, getContext(), R.layout.item_club_pending, this);
        RecyclerView.LayoutManager pendingClubsLayoutManager = new LinearLayoutManager(pendingClubsAdapter.getContext());
        recyclerViewPendingClubs.setLayoutManager(pendingClubsLayoutManager);
        recyclerViewPendingClubs.setAdapter(pendingClubsAdapter);

        prepareClubsData();
        prepareJoinedClubsData();
        preparePendingClubsData();
    }

    private void prepareClubsData() {
        clubsList.add(new Clubs(1, 1, "Running", "Description", 1, 2, 3));
        clubsList.add(new Clubs(2, 1, "Tennis", "Description", 1, 2, 3));
        clubsList.add(new Clubs(1, 1, "Running", "Description", 1, 2, 3));

        clubsAdapter.notifyDataSetChanged();
    }

    private void prepareJoinedClubsData() {
        joinedClubsList.add(new Clubs(1, 1, "Running", "Description", 1, 2, 3));
        joinedClubsList.add(new Clubs(2, 1, "Tennis", "Description", 1, 2, 3));
        joinedClubsList.add(new Clubs(2, 1, "Biking", "Description", 1, 2, 3));

        joinedClubsAdapter.notifyDataSetChanged();
    }

    private void preparePendingClubsData() {
        pendingClubsList.add(new Clubs(1, 1, "Running", "Description", 1, 2, 3));
        pendingClubsList.add(new Clubs(2, 1, "Tennis", "Description", 1, 2, 3));
        pendingClubsList.add(new Clubs(2, 1, "Biking", "Description", 1, 2, 3));

        pendingClubsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClubsClick(Clubs club) {
        Toast.makeText(getContext(), club.getName(), Toast.LENGTH_SHORT).show();
    }
}