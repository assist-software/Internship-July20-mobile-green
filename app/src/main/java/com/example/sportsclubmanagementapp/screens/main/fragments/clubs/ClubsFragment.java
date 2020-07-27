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
    //recyclers
    private RecyclerView recyclerViewUnJoinedClubs;
    private RecyclerView recyclerViewJoinedClubs;
    private RecyclerView recyclerViewPendingClubs;
    // adapters
    private ClubsAdapter unJoinedClubsAdapter;
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
        setToolbarTitle();
        setToolbarIconNavigation();
    }

    private void setToolbarAvatar() {
        CircleImageView avatar_toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.avatar_toolbar);
        avatar_toolbar.setVisibility(View.VISIBLE); //set the avatar visible (because is hidden for home fragment)
        Utils.setCircleAvatar(getActivity(), Objects.requireNonNull((MainActivity) getActivity()).getAvatar(), avatar_toolbar);
    }

    private void setToolbarTitle() {
        TextView fragment_title = Objects.requireNonNull(getActivity()).findViewById(R.id.fragment_title);
        fragment_title.setText(getResources().getText(R.string.clubs_txt));
    }

    private void setToolbarIconNavigation() {
        Toolbar toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(null);
        toolbar.setTitle("");
        toolbar.setNavigationOnClickListener(v -> startActivity(new Intent(getActivity(), MyProfileActivity.class)));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setUpAllRecyclerViews(view);
        geUnJoinedClubsApi();
        getJoinedClubsApi();
        getPendingClubsApi();
    }

    private void setUpAllRecyclerViews(View view) {
        recyclerViewUnJoinedClubs = view.findViewById(R.id.new_clubs_recycler_view);
        recyclerViewJoinedClubs = view.findViewById(R.id.joined_clubs_recycler_view);
        recyclerViewPendingClubs = view.findViewById(R.id.pending_clubs_recycler_view);
    }

    private void getPendingClubsApi() {
        Call<List<Club>> call = ApiHelper.getApi().getPendingClubs(getToken());
        call.enqueue(new Callback<List<Club>>() {
            @Override
            public void onResponse(@NotNull Call<List<Club>> call, @NotNull Response<List<Club>> response) {
                if (!response.isSuccessful())
                    Toast.makeText(getActivity(), R.string.api_response_not_successful, Toast.LENGTH_SHORT).show();
                else {
                    pendingClubsAdapter = initAdapter(response.body(), recyclerViewPendingClubs, 3);
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Club>> call, @NotNull Throwable t) {
                Toast.makeText(getActivity(), R.string.api_failure + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getJoinedClubsApi() {
        Call<List<Club>> call = ApiHelper.getApi().getJoinedClubs(getToken());
        call.enqueue(new Callback<List<Club>>() {
            @Override
            public void onResponse(@NotNull Call<List<Club>> call, @NotNull Response<List<Club>> response) {
                if (!response.isSuccessful())
                    Toast.makeText(getActivity(), R.string.api_response_not_successful, Toast.LENGTH_LONG).show();
                else {
                    initAdapter(response.body(), recyclerViewJoinedClubs, 2);
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Club>> call, @NotNull Throwable t) {
                Toast.makeText(getActivity(), R.string.api_failure + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void geUnJoinedClubsApi() {
        Call<List<Club>> call = ApiHelper.getApi().getUnJoinedClubs(getToken());
        call.enqueue(new Callback<List<Club>>() {
            @Override
            public void onResponse(@NotNull Call<List<Club>> call, @NotNull Response<List<Club>> response) {
                if (!response.isSuccessful())
                    Toast.makeText(getActivity(), R.string.api_response_not_successful, Toast.LENGTH_LONG).show();
                else {
                    unJoinedClubsAdapter = initAdapter(response.body(), recyclerViewUnJoinedClubs, 1);
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
        return "token " + prefs.getString(getString(R.string.user_token), getString(R.string.no_token_prefs));
    }

    private ClubsAdapter initAdapter(List<Club> clubs, RecyclerView recyclerView, int layout) {
        ClubsAdapter adapter = new ClubsAdapter(clubs, getContext(), layout, this);
        RecyclerView.LayoutManager clubsLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(clubsLayoutManager);
        recyclerView.setAdapter(adapter);
        return adapter;
    }

    @Override
    public void onClubsClick(Club club) { //open screen with club details
        Intent intent = new Intent(getActivity(), ClubPageActivity.class);
        intent.putExtra("CLUB_EXTRA_SESSION_ID", club);
        startActivity(intent);
    }

    @Override
    public void onClubsJoinClick(Club club) { //process clubs locally, and api post call to join club
        clubJoinApi(club);
        Toast.makeText(getContext(), "Club: " + club.getName() + " " + getString(R.string.club_joined_successfully), Toast.LENGTH_LONG).show();
    }

    private void clubJoinApi(Club club) {
        Call<Void> call = ApiHelper.getApi().createPostUserJoinClub(getToken(), club.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), getString(R.string.api_response_not_successful) + response.code(), Toast.LENGTH_SHORT).show();
                } else {
                    unJoinedClubsAdapter.removeClub(club);
                    pendingClubsAdapter.addClub(club);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                Toast.makeText(getActivity(), R.string.api_failure + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}