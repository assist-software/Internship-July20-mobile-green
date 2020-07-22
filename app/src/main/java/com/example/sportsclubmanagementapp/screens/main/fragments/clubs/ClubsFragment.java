package com.example.sportsclubmanagementapp.screens.main.fragments.clubs;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.example.sportsclubmanagementapp.screens.club_page.ClubPageActivity;
import com.example.sportsclubmanagementapp.screens.main.fragments.home.ClubsAdapter;
import com.example.sportsclubmanagementapp.screens.main.fragments.home.OnClubItemListener;
import com.example.sportsclubmanagementapp.screens.myprofile.MyProfileActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.transform.Templates;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setToolbar();
        return inflater.inflate(R.layout.fragment_clubs, container, false);
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getText(R.string.clubs)); //get the toolbar name from strings

        toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.my_profile_toolbar, null));
        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MyProfileActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //for new events recycler
        recyclerViewClubs = (RecyclerView) view.findViewById(R.id.new_clubs_recycler_view);
        setUpNewEventsRecyclerView();

        //for joined events recycler
        recyclerViewJoinedClubs = (RecyclerView) view.findViewById(R.id.joined_clubs_recycler_view);
        setUpJoinedEventsRecyclerView();

        //for pending events recycler
        recyclerViewPendingClubs = (RecyclerView) view.findViewById(R.id.pending_clubs_recycler_view);
        setUpPendingEventsRecyclerView();

        prepareClubsData();
        prepareJoinedClubsData();
        preparePendingClubsData();
    }

    private void setUpNewEventsRecyclerView() {
        clubsAdapter = new ClubsAdapter(clubsList, getContext(), ClubsAdapter.JOIN_CLUB_LAYOUT,this);
        RecyclerView.LayoutManager clubsLayoutManager = new LinearLayoutManager(clubsAdapter.getContext());
        recyclerViewClubs.setLayoutManager(clubsLayoutManager);
        recyclerViewClubs.setAdapter(clubsAdapter);
    }

    private void setUpJoinedEventsRecyclerView() {
        joinedClubsAdapter = new ClubsAdapter(joinedClubsList, getContext(), ClubsAdapter.JOINED_CLUB_LAYOUT,this);
        RecyclerView.LayoutManager joinedClubsLayoutManager = new LinearLayoutManager(joinedClubsAdapter.getContext());
        recyclerViewJoinedClubs.setLayoutManager(joinedClubsLayoutManager);
        recyclerViewJoinedClubs.setAdapter(joinedClubsAdapter);
    }

    private void setUpPendingEventsRecyclerView() {
        pendingClubsAdapter = new ClubsAdapter(pendingClubsList, getContext(), ClubsAdapter.PENDING_CLUB_LAYOUT,this);
        RecyclerView.LayoutManager pendingClubsLayoutManager = new LinearLayoutManager(pendingClubsAdapter.getContext());
        recyclerViewPendingClubs.setLayoutManager(pendingClubsLayoutManager);
        recyclerViewPendingClubs.setAdapter(pendingClubsAdapter);
    }

    @Override
    public void onClubsClick(Clubs club) {
        Intent intent = new Intent(getActivity(), ClubPageActivity.class);
        intent.putExtra("CLUB_EXTRA_SESSION_ID", club); //send the clicked club to the next activity (its page)
        startActivity(intent);
    }

    @Override
    public void onClubsJoinClick(Clubs club) {
        //remove the joined club from the list
        clubsList.remove(club);
        pendingClubsList.add(club);

        //hide recycler header if the list is empty
        if( clubsList.isEmpty() )
            Objects.requireNonNull(getActivity()).findViewById(R.id.new_club).setVisibility(View.GONE);
        else
            Objects.requireNonNull(getActivity()).findViewById(R.id.new_club).setVisibility(View.VISIBLE);
        if( pendingClubsList.isEmpty() )
            Objects.requireNonNull(getActivity()).findViewById(R.id.pending_club).setVisibility(View.GONE);
        else
            Objects.requireNonNull(getActivity()).findViewById(R.id.pending_club).setVisibility(View.VISIBLE);

        //notify adapters to delete and add the club from recyclers
        clubsAdapter.notifyDataSetChanged();
        joinedClubsAdapter.notifyDataSetChanged();
        pendingClubsAdapter.notifyDataSetChanged();

        //show message to user with the joined club
        Toast.makeText(getActivity(), "Joined to " + club.getName(), Toast.LENGTH_SHORT).show();
    }

    public static ClubsFragment newInstance() {
        return new ClubsFragment();
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
}