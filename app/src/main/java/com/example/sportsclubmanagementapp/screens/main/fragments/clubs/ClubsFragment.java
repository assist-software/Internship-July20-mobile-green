package com.example.sportsclubmanagementapp.screens.main.fragments.clubs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Club;
import com.example.sportsclubmanagementapp.data.retrofit.ApiHelper;
import com.example.sportsclubmanagementapp.screens.club_page.ClubPageActivity;
import com.example.sportsclubmanagementapp.screens.main.MainActivity;
import com.example.sportsclubmanagementapp.screens.main.fragments.home.ClubsAdapter;
import com.example.sportsclubmanagementapp.screens.main.fragments.home.OnClubItemListener;
import com.example.sportsclubmanagementapp.screens.myprofile.MyProfileActivity;
import com.example.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClubsFragment extends Fragment implements OnClubItemListener {

    //for new clubs recycler
    private List<Club> clubList;
    private RecyclerView recyclerViewClubs;
    private ClubsAdapter clubsAdapter;

    //for joined clubs recycler
    private List<Club> joinedClubList;
    private RecyclerView recyclerViewJoinedClubs;
    private ClubsAdapter joinedClubsAdapter;

    //for pending clubs recycler
    private List<Club> pendingClubList;
    private RecyclerView recyclerViewPendingClubs;
    private ClubsAdapter pendingClubsAdapter;

    public static ClubsFragment newInstance() {
        return new ClubsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setToolbar();
        return inflater.inflate(R.layout.fragment_clubs, container, false);
    }

    private void setToolbar() {
        setToolbarAvatar();

        TextView fragment_title = Objects.requireNonNull(getActivity()).findViewById(R.id.fragment_title);
        fragment_title.setText(getResources().getText(R.string.clubs_txt));

        Toolbar toolbar = (Toolbar) Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(null);
        toolbar.setTitle("");
        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MyProfileActivity.class);
            startActivity(intent);
        });
    }

    private void setToolbarAvatar() {
        MainActivity mainActivity = (MainActivity) getActivity();
        CircleImageView avatar_toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.avatar_toolbar);
        avatar_toolbar.setVisibility(View.VISIBLE); //set the avatar visible (because is hidden for home fragment)
        Utils.setCircleAvatar(mainActivity, Objects.requireNonNull(mainActivity).getAvatar(), avatar_toolbar);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        geUnJoinedClubsApi();
        getJoinedClubsApi();
        getPendingClubsApi();
        setUpAllRecyclerViews(view);
        setUpPendingEventsRecyclerView();
    }

    private void getPendingClubsApi() {
        String token = getToken();
        Call<List<Club>> call = ApiHelper.getApi().getPendingClubs(token);
        call.enqueue(new Callback<List<Club>>() {
            @Override
            public void onResponse(@NotNull Call<List<Club>> call, @NotNull Response<List<Club>> response) {
                if (!response.isSuccessful())
                    Toast.makeText(getActivity(), R.string.api_response_not_successful, Toast.LENGTH_LONG).show();
                else {
                    pendingClubList = response.body();
                    setUpPendingEventsRecyclerView();
                    preparePendingClubsData();
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Club>> call, @NotNull Throwable t) {
                Toast.makeText(getActivity(), R.string.api_failure + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getJoinedClubsApi() {
        String token = getToken();
        Call<List<Club>> call = ApiHelper.getApi().getJoinedClubs(token);
        call.enqueue(new Callback<List<Club>>() {
            @Override
            public void onResponse(@NotNull Call<List<Club>> call, @NotNull Response<List<Club>> response) {
                if (!response.isSuccessful())
                    Toast.makeText(getActivity(), R.string.api_response_not_successful, Toast.LENGTH_LONG).show();
                else {
                    joinedClubList = response.body();
                    setUpJoinedEventsRecyclerView();
                    prepareJoinedClubsData();
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Club>> call, @NotNull Throwable t) {
                Toast.makeText(getActivity(), R.string.api_failure + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void geUnJoinedClubsApi() {
        String token = getToken();
        Call<List<Club>> call = ApiHelper.getApi().getUnJoinedClubs(token);
        call.enqueue(new Callback<List<Club>>() {
            @Override
            public void onResponse(@NotNull Call<List<Club>> call, @NotNull Response<List<Club>> response) {
                if (!response.isSuccessful())
                    Toast.makeText(getActivity(), R.string.api_response_not_successful, Toast.LENGTH_LONG).show();
                else {
                    clubList = response.body();
                    setUpNewClubsRecycleView();
                    prepareClubsData();
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Club>> call, @NotNull Throwable t) {
                Toast.makeText(getActivity(), R.string.api_failure + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private String getToken() {
        getActivity();
        SharedPreferences prefs = Objects.requireNonNull(getActivity()).getSharedPreferences(getString(R.string.MY_PREFS_NAME), Context.MODE_PRIVATE);
        return "token " + prefs.getString(getString(R.string.user_token), "no token");
    }

    private void setUpAllRecyclerViews(View view) {
        //for new clubs recycler
        recyclerViewClubs = view.findViewById(R.id.new_clubs_recycler_view);
        //for joined events recycler
        recyclerViewJoinedClubs = view.findViewById(R.id.joined_clubs_recycler_view);
        //for pending events recycler
        recyclerViewPendingClubs = view.findViewById(R.id.pending_clubs_recycler_view);
    }

    private void setUpNewClubsRecycleView() {
        clubsAdapter = new ClubsAdapter(clubList, getContext(), ClubsAdapter.JOIN_CLUB_LAYOUT, this);
        RecyclerView.LayoutManager clubsLayoutManager = new LinearLayoutManager(clubsAdapter.getContext());
        recyclerViewClubs.setLayoutManager(clubsLayoutManager);
        recyclerViewClubs.setAdapter(clubsAdapter);
    }

    private void setUpJoinedEventsRecyclerView() {
        joinedClubsAdapter = new ClubsAdapter(joinedClubList, getContext(), ClubsAdapter.JOINED_CLUB_LAYOUT, this);
        RecyclerView.LayoutManager joinedClubsLayoutManager = new LinearLayoutManager(joinedClubsAdapter.getContext());
        recyclerViewJoinedClubs.setLayoutManager(joinedClubsLayoutManager);
        recyclerViewJoinedClubs.setAdapter(joinedClubsAdapter);
    }

    private void setUpPendingEventsRecyclerView() {
        pendingClubsAdapter = new ClubsAdapter(pendingClubList, getContext(), ClubsAdapter.PENDING_CLUB_LAYOUT, this);
        RecyclerView.LayoutManager pendingClubsLayoutManager = new LinearLayoutManager(pendingClubsAdapter.getContext());
        recyclerViewPendingClubs.setLayoutManager(pendingClubsLayoutManager);
        recyclerViewPendingClubs.setAdapter(pendingClubsAdapter);
    }

    @Override
    public void onClubsClick(Club club) {
        Intent intent = new Intent(getActivity(), ClubPageActivity.class);
        intent.putExtra("CLUB_EXTRA_SESSION_ID", club); //send the clicked club to the next activity (its page)
        startActivity(intent);
    }

    @Override
    public void onClubsJoinClick(Club club) {
        //remove the joined club from the list
        clubList.remove(club);
        pendingClubList.add(club);

        //hide recycler header if the list is empty
        if (clubList.isEmpty())
            Objects.requireNonNull(getActivity()).findViewById(R.id.new_club).setVisibility(View.GONE);
        else
            Objects.requireNonNull(getActivity()).findViewById(R.id.new_club).setVisibility(View.VISIBLE);
        if (pendingClubList.isEmpty())
            Objects.requireNonNull(getActivity()).findViewById(R.id.pending_club).setVisibility(View.GONE);
        else
            Objects.requireNonNull(getActivity()).findViewById(R.id.pending_club).setVisibility(View.VISIBLE);

        //notify adapters to delete and add the club from recyclers
        clubsAdapter.notifyDataSetChanged();
        joinedClubsAdapter.notifyDataSetChanged();
        pendingClubsAdapter.notifyDataSetChanged();

        //show message to user with the joined club
        Toast.makeText(getActivity(), "Joined to " + club.getName(), Toast.LENGTH_SHORT).show();
        clubPutApi((int) club.getId());
    }

    private void clubPutApi(int id) {
        String token = getToken();
        Call<Void> call = ApiHelper.getApi().createPostUserJoinClub(token, id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                if (!response.isSuccessful())
                    Toast.makeText(getActivity(), getString(R.string.api_response_not_successful) + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                Toast.makeText(getActivity(), R.string.api_failure + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void prepareClubsData() {
        clubsAdapter.notifyDataSetChanged();
    }

    private void prepareJoinedClubsData() {
        joinedClubsAdapter.notifyDataSetChanged();
    }

    private void preparePendingClubsData() {
        pendingClubsAdapter.notifyDataSetChanged();
    }
}