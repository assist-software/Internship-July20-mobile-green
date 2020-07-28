package com.example.sportsclubmanagementapp.screens.eventdetails;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Event;
import com.example.sportsclubmanagementapp.data.models.EventDetails;
import com.example.sportsclubmanagementapp.data.models.User;
import com.example.sportsclubmanagementapp.data.retrofit.ApiHelper;
import com.example.sportsclubmanagementapp.screens.addworkout.AddWorkoutActivity;
import com.example.sportsclubmanagementapp.screens.club_page.UserAdapter;
import com.example.utils.Utils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {
    public static final int HEART_RATE_DATA = 1;
    public static final int CALORIES_DATA = 2;
    public static final int AVG_SPEED_DATA = 3;
    public static final int DISTANCE_DATA = 4;

    private TextView title;
    private TextView date;
    private TextView time;
    private TextView location;
    private TextView description1;
    private TextView description2;
    private TextView titleParticipants;
    private ImageView eventImage;
    private TextView titleSelectedDataChart;//text above the chart

    //for members list recycler
    private List<User> selectedMembersChart = new ArrayList<>();
    private UserAdapter userAdapter;
    private BarChart barChart;
    private List<CheckBox> checkBoxes = new ArrayList<>();
    private int selectedDataForChart; //the type of data selected from the check boxes (under chart)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        initComponents();
        setToolbar();
        setEventStatus();
        setEventAndMembersChart();
        startChartData(); //function for set up chart and select default data
    }

    private void initComponents() {
        this.title = findViewById(R.id.eventTitleTextView);
        this.date = findViewById(R.id.dateEventTextView);
        this.time = findViewById(R.id.timeEventTextView);
        this.location = findViewById(R.id.eventLocationTextView);
        this.description1 = findViewById(R.id.eventDescription1TextView);
        this.description2 = findViewById(R.id.eventDescription2TextView);
        this.titleParticipants = findViewById(R.id.eventParticipantsTextView);
        this.eventImage = findViewById(R.id.image);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(EventDetailsActivity.this);
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.eventDetailsActivityToolbar);
        toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_back_toolbar, null));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void setEventStatus() {
        if (getIntent().getExtras() != null) {
            int[] status = (int[]) getIntent().getSerializableExtra(getString(R.string.event_status));
            Button button = findViewById(R.id.eventStatusButton);
            if (status == null || status[0] == 0) {
                makeStatusUnJoined(button);
            } else {
                makeStatusJoined(button);
            }
        }
    }

    private void setEventAndMembersChart() {
        if (getIntent().getExtras() != null) {
            int id = (int) getIntent().getSerializableExtra(getString(R.string.event_id));
            getApiEventDetails(id);
        }
    }

    private void getApiEventDetails(int id) {
        Call<EventDetails> call = ApiHelper.getApi().getEventDetails(getToken(), id);
        call.enqueue(new Callback<EventDetails>() {
            @Override
            public void onResponse(@NotNull Call<EventDetails> call, @NotNull Response<EventDetails> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(EventDetailsActivity.this, R.string.api_response_not_successful, Toast.LENGTH_SHORT).show();
                } else {
                    assert response.body() != null;
                    userAdapter = initMembersChartAdapter(response.body().getMembers());
                    setContent(response.body().getData(), response.body().getMembers().size());
                }
            }

            @Override
            public void onFailure(@NotNull Call<EventDetails> call, @NotNull Throwable t) {
                Toast.makeText(EventDetailsActivity.this, R.string.api_failure + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private String getToken() {
        SharedPreferences prefs = this.getSharedPreferences(getString(R.string.MY_PREFS_NAME), Context.MODE_PRIVATE);
        return "token " + prefs.getString(getString(R.string.user_token), getString(R.string.no_token_prefs));
    }

    private UserAdapter initMembersChartAdapter(List<User> members) {
        RecyclerView recyclerViewUsers = findViewById(R.id.members_recycler_view);
        UserAdapter userAdapter = new UserAdapter(members, this, UserAdapter.MEMBER_BAR_WITH_CHECK_BOX, EventDetailsActivity.this);
        RecyclerView.LayoutManager usersLayoutManager = new LinearLayoutManager(userAdapter.getContext());
        recyclerViewUsers.setLayoutManager(usersLayoutManager);
        recyclerViewUsers.setAdapter(userAdapter);
        return userAdapter;
    }

    @SuppressLint("SetTextI18n")
    private void setContent(Event event, int numberOfMembers) {
        this.title.setText(event.getName());
        this.date.setText(event.getDate());
        this.time.setText(event.getTime());
        this.location.setText(event.getLocation());
        this.description1.setText(event.getDescription1());
        this.description2.setText(event.getDescription2());
        this.titleParticipants.setText(getString(R.string.participants_chart) + numberOfMembers + ")");
        this.eventImage.setImageDrawable(Utils.getEventsPictures(getBaseContext()).get(new Random().nextInt(5)));
    }

    //function for set up chart and select default data
    private void startChartData() {
        setUpCheckBoxes();
        setUpCheckBoxesListeners();
        //set heart rate data by default
        titleSelectedDataChart.setText(getResources().getText(R.string.heart_rate_txt));
        checkBoxes.get(HEART_RATE_DATA - 1).setChecked(true); //auto check the first check box (heart rate)
        selectedDataForChart = HEART_RATE_DATA;
        setUpChart(); //set up chart data with heart rate (first)
    }

    private void setUpCheckBoxes() {
        barChart = findViewById(R.id.barChart);
        titleSelectedDataChart = findViewById(R.id.chartDataSelected);
        checkBoxes.add(findViewById(R.id.heart_rate));
        checkBoxes.add(findViewById(R.id.calories));
        checkBoxes.add(findViewById(R.id.speed));
        checkBoxes.add(findViewById(R.id.distance));
    }

    private void setUpCheckBoxesListeners() {
        String[] titlesForSelectedData = new String[]{
                (String) getResources().getText(R.string.heart_rate_txt),
                (String) getResources().getText(R.string.calories_txt1),
                (String) getResources().getText(R.string.average_speed),
                (String) getResources().getText(R.string.distance)};
        //set listeners for the check boxes (under chart)
        for (int i = 0; i < 4; i++) {
            int finalI = i;
            checkBoxes.get(i).setOnClickListener(v -> {
                if (selectedDataForChart != finalI + 1) { //if the selected box is not the current selected
                    checkBoxes.get(selectedDataForChart - 1).setChecked(false); //uncheck the current box
                    selectedDataForChart = finalI + 1;
                    checkBoxes.get(selectedDataForChart - 1).setChecked(true); //check the selected box
                    setUpChart(); //change the chart according to the new selected data
                } else
                    checkBoxes.get(selectedDataForChart - 1).setChecked(true); //keep the selected box checked
                titleSelectedDataChart.setText(titlesForSelectedData[finalI]);
            });
        }
    }

    public void setParticipants() {
        selectedMembersChart = userAdapter.getSelectedUsers();
        setUpChart();
    }

    private void setUpChart() {
        List<BarEntry> list = new ArrayList<>(); //list of data for every participant
        final ArrayList<String> xAxisLabel = new ArrayList<>(); //the name of each participant (for x coordinate)
        //set the first date with the current user (logged in the app)
        int maximumValue = 0;
        if (selectedDataForChart == HEART_RATE_DATA) maximumValue = 150;
        else if (selectedDataForChart == CALORIES_DATA) maximumValue = 5000;
        else if (selectedDataForChart == AVG_SPEED_DATA) maximumValue = 80;
        else if (selectedDataForChart == DISTANCE_DATA) maximumValue = 100;
        else assert true;
        //set chart data for current user
        list.add(new BarEntry(0, new Random().nextInt(maximumValue)));
        xAxisLabel.add(0, "You");
        for (int i = 0; i < selectedMembersChart.size(); i++) {
            list.add(new BarEntry(i + 1, new Random().nextInt(maximumValue)));
            //take the first name of the user for x coordinate
            xAxisLabel.add(i + 1, selectedMembersChart.get(i).getFirst_name());
        }
        setAppearanceForChart(xAxisLabel, list); //set the date in the chart and modify the default appearance
    }

    private void setAppearanceForChart(ArrayList<String> xAxisLabel, List<BarEntry> list) {
        BarData barData = new BarData(); //all the data for chart
        BarDataSet dataSet = new BarDataSet(list, ""); //data set, which is only one (one of those 4)
        dataSet.setColor(Color.BLACK);
        dataSet.setValueTextSize(15f);
        barData.addDataSet(dataSet);

        //set up the x coordinate
        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);
        //xAxis.setLabelRotationAngle(-45);

        //modify the default appearance
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getLegend().setEnabled(false);
        barChart.getDescription().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getXAxis().setTextSize(14);
        barChart.setExtraOffsets(0, 0, 0, 1);
        barChart.getAxisLeft().setTextSize(14);
        barChart.setData(barData);
        barChart.invalidate();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng assist = new LatLng(47.640121, 26.259330);
        googleMap.addMarker(new MarkerOptions().position(assist).title("ASSIST"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(assist, 17f));
        googleMap.addCircle(new CircleOptions()
                .center(assist)
                .radius(100)
                .strokeColor(Color.argb(128, 0, 0, 255))
                .strokeWidth(5f)
                .fillColor(Color.argb(80, 0, 0, 255)));
        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    }

    public void changeEventStatus(View view) {
        Button button = findViewById(R.id.eventStatusButton);
        String buttonText = button.getText().toString();
        if (buttonText.toLowerCase().equals("joined")) {
            makeStatusUnJoined(button);
            Toast.makeText(this, R.string.event_unjoined, Toast.LENGTH_SHORT).show();
        } else {
            makeStatusJoined(button);
            Toast.makeText(this, R.string.event_joined, Toast.LENGTH_SHORT).show();
        }
    }

    private void makeStatusUnJoined(Button button) {
        button.setText(getResources().getText(R.string.join_txt));
        button.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_rounded_8dp_black, null));
        button.setTextColor(getColor(R.color.colorAccent));
    }

    private void makeStatusJoined(Button button) {
        button.setText(getResources().getText(R.string.joined_txt));
        button.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_rounded_8dp_light_gray, null));
        button.setTextColor(getColor(R.color.colorPrimary));
    }

    public void goToAddWorkoutScreen(View view) {
        startActivity(new Intent(this, AddWorkoutActivity.class));
    }
}