package com.example.sportsclubmanagementapp.screens.main.fragments.home;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Event;
import com.example.sportsclubmanagementapp.data.models.Clubs;
import com.example.sportsclubmanagementapp.data.models.FutureEvents;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Future;

public class HomeFragment extends Fragment {

    //for events list
    private List<Event> eventList = new ArrayList<>();
    private RecyclerView recyclerViewEvents;
    private EventAdapter eventAdapter;

    //for first club fragment
    private List<Clubs> firstClubList = new ArrayList<>();
    private RecyclerView recyclerViewFirstClub;
    private ClubsAdapter firstClubAdapter;

    //for clubs fragment
    private List<Clubs> clubsList = new ArrayList<>();
    private RecyclerView recyclerViewClubs;
    private ClubsAdapter ClubsAdapter;

    //for future events fragment
    private List<FutureEvents> futureEventsList = new ArrayList<>();
    private RecyclerView recyclerViewFutureEvents;
    private FutureEventsAdapter futureEventsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public static HomeFragment newInstance() {
        return  new HomeFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        displayAvatar();

        //for events recycler
        recyclerViewEvents = (RecyclerView) view.findViewById(R.id.events_recycler_view);
        eventAdapter = new EventAdapter(eventList, getContext());
        RecyclerView.LayoutManager eventLayoutManager = new LinearLayoutManager(eventAdapter.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewEvents.setLayoutManager(eventLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerViewEvents.setAdapter(eventAdapter);

        //for first club recycler
        recyclerViewFirstClub = (RecyclerView) view.findViewById(R.id.first_club_recycler_view);
        firstClubAdapter = new ClubsAdapter(firstClubList, getContext());
        RecyclerView.LayoutManager firstClubLayoutManager = new LinearLayoutManager(firstClubAdapter.getContext());
        recyclerViewFirstClub.setLayoutManager(firstClubLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFirstClub.setAdapter(firstClubAdapter);

        //for clubs recycler
        recyclerViewClubs = (RecyclerView) view.findViewById(R.id.join_clubs_recycler_view);
        ClubsAdapter = new ClubsAdapter(clubsList, getContext());
        RecyclerView.LayoutManager ClubsLayoutManager = new LinearLayoutManager(ClubsAdapter.getContext());
        recyclerViewClubs.setLayoutManager(ClubsLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerViewClubs.setAdapter(ClubsAdapter);

        //for future events recycler
        recyclerViewFutureEvents = (RecyclerView) view.findViewById(R.id.future_events_recycler_view);
        futureEventsAdapter = new FutureEventsAdapter(futureEventsList, getContext());
        RecyclerView.LayoutManager futureEventsLayoutManager = new LinearLayoutManager(futureEventsAdapter.getContext());
        recyclerViewFutureEvents.setLayoutManager(futureEventsLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFutureEvents.setAdapter(futureEventsAdapter);

        prepareEventData();
        prepareFirstClubsData();
        prepareClubsData();
        prepareFutureEventsData();
    }

    private void displayAvatar() {
        ImageView avatar_icon = (ImageView) Objects.requireNonNull(getView()).findViewById(R.id.avatar);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(50));
        Glide.with(this).load(R.drawable.ic_default_avatar).apply(RequestOptions.bitmapTransform(new RoundedCorners(34))).into(avatar_icon);
    }

    private void prepareEventData() {
        eventList.add(new Event(1, 1, "Running for Life", "Description", "Suceava", "16.07.2020", 10, "Running", 2, 3, 1));
        eventList.add(new Event(2, 1, "Cycle for Life", "Description", "Suceava", "16.07.2020", 10, "Running", 2, 3, 1));
        eventList.add(new Event(3, 2, "Motors for Life", "Description", "Suceava", "16.07.2020", 10, "Running", 2, 3, 1));
        eventList.add(new Event(4, 3, "Football for Life", "Description", "Suceava", "16.07.2020", 10, "Running", 2, 3, 1));

        eventAdapter.notifyDataSetChanged();
    }

    private void prepareClubsData() {
        firstClubList.add(new Clubs(1, 1, "Running", "Description", 1, 2, 3));
        firstClubList.add(new Clubs(2, 1, "Tennis", "Description", 1, 2, 3));

        firstClubAdapter.notifyDataSetChanged();
    }

    private void prepareFirstClubsData() {
        clubsList.add(new Clubs(1, 1, "Biking", "Description", 1, 2, 3));
        clubsList.add(new Clubs(2, 1, "Running", "Description", 1, 2, 3));
        clubsList.add(new Clubs(3, 1, "Tennis", "Description", 1, 2, 3));

        ClubsAdapter.notifyDataSetChanged();
    }

    private void prepareFutureEventsData() {
        futureEventsList.add(new FutureEvents(1, 1, "Running for Life", "Description", "Suceava", "16.07.2020", 10, "Running", 2, 3, 1));
        futureEventsList.add(new FutureEvents(2, 1, "Cycle for Life", "Description", "Suceava", "16.07.2020", 10, "Running", 2, 3, 1));
        futureEventsList.add(new FutureEvents(3, 2, "Motors for Life", "Description", "Suceava", "16.07.2020", 10, "Running", 2, 3, 1));

        futureEventsAdapter.notifyDataSetChanged();
    }
}