package com.example.sportsclubmanagementapp.screens.EventDetails;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportsclubmanagementapp.R;
import com.example.sportsclubmanagementapp.data.models.Event;
import com.example.sportsclubmanagementapp.data.models.Role;
import com.example.sportsclubmanagementapp.data.models.User;
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

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class EventDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private List<Drawable> eventPictures;

    private TextView title;
    private TextView date;
    private TextView time;
    private TextView location;
    private TextView description1;
    private TextView description2;

    private Event event;
    //for members list recycler
    private List<User> usersList = new ArrayList<>();
    private UserAdapter userAdapter;
    private BarChart barChart;
    private List<User> participants = new ArrayList<>();
    private List<CheckBox> checkBoxes = new ArrayList<>();
    private int selectedDataForChart; //the type of data selected from the check boxes (under chart)
    private TextView titleSelectedDataChart; //text above the chart

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        initComponents();
        setUpUsersRecyclerView(); //for users recycler

        prepareUsersData(); //for TESTS
        eventPictures = Utils.getEventsPictures(getBaseContext()); //for TESTS
        TextView title = findViewById(R.id.eventParticipantsTextView);
        title.setText("Participants (" + String.valueOf(usersList.size()) + ")");
        ImageView image = findViewById(R.id.image);
        image.setImageDrawable(eventPictures.get(new Random().nextInt(5)));
        TextView date = findViewById(R.id.dateEventTextView);
        //date.setText(event.getDate());
        TextView location = findViewById(R.id.eventLocationTextView);
        //location.setText(event.getLocation());
        TextView firstDescription = findViewById(R.id.eventDescription1TextView);
        //firstDescription.setText(event.getDescription());
        TextView secondDescription = findViewById(R.id.eventDescription2TextView);
        //secondDescription.setText(event.getDescription() + " secondary");

        setToolbar();
        setEvent();
    }

    private void initComponents() {
        title = findViewById(R.id.eventTitleTextView);
        date = findViewById(R.id.dateEventTextView);
        time = findViewById(R.id.timeEventTextView);
        location = findViewById(R.id.eventLocationTextView);
        description1 = findViewById(R.id.eventDescription1TextView);
        description2 = findViewById(R.id.eventDescription2TextView);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(EventDetailsActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startChartData(); //function for set up chart and select default data
    }

    //function for set up chart and select default data
    private void startChartData() {
        setUpCheckBoxes();
        setUpCheckBoxesListeners();
        //set heart rate data by default
        titleSelectedDataChart.setText(getResources().getText(R.string.heart_rate_txt));
        checkBoxes.get(0).setChecked(true); //auto check the first check box (heart rate)
        selectedDataForChart = 1;
        setUpChart(); //set up chart data with heart rate (first)
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

    private void setUpCheckBoxes() {
        barChart = findViewById(R.id.barChart);
        titleSelectedDataChart = findViewById(R.id.chartDataSelected);
        checkBoxes.add(findViewById(R.id.heart_rate));
        checkBoxes.add(findViewById(R.id.calories));
        checkBoxes.add(findViewById(R.id.speed));
        checkBoxes.add(findViewById(R.id.distance));
    }

    public void setParticipants() {
        participants = userAdapter.getSelectedUsers();
        setUpChart();
    }

    private void setUpChart() {
        List<BarEntry> list = new ArrayList<>(); //list of data for every participant
        final ArrayList<String> xAxisLabel = new ArrayList<>(); //the name of each participant (for x coordinate)
        //set the first date with the current user (logged in the app)
        list.add(new BarEntry(0, 1));
        xAxisLabel.add(0, "You");

        for (int i = 0; i < participants.size(); i++) {
            list.add(new BarEntry(i + 1, 2));
            //take the first name of the user for x coordinate
            xAxisLabel.add(i + 1, participants.get(i).getFirst_and_last_name().split(" ")[0]);
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

    private void setUpUsersRecyclerView() {
        RecyclerView recyclerViewUsers = findViewById(R.id.members_recycler_view);
        userAdapter = new UserAdapter(usersList, this, UserAdapter.MEMBER_BAR_WITH_CHECK_BOX, EventDetailsActivity.this);
        RecyclerView.LayoutManager usersLayoutManager = new LinearLayoutManager(userAdapter.getContext());
        recyclerViewUsers.setLayoutManager(usersLayoutManager);
        recyclerViewUsers.setAdapter(userAdapter);
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.eventDetailsActivityToolbar);
        toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_back_toolbar, null));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void setEvent() {
        if (getIntent().getExtras() != null) {
            event = (Event) getIntent().getSerializableExtra("eventObject");
            setContent();
        }
    }

    @SuppressLint("SetTextI18n")
    private void setContent() {
        title.setText(event.getName());
        date.setText(event.getDate());
        time.setText(event.getDate());
        location.setText(event.getLocation());
        description1.setText(event.getDescription());
        description2.setText(event.getDescription());

        //for TESTS
        TextView titleParticipants = findViewById(R.id.eventParticipantsTextView);
        titleParticipants.setText("Participants (" + usersList.size() + ")");
    }

    public void goToAddWorkoutScreen(View view) {
        startActivity(new Intent(this, AddWorkoutActivity.class));
    }

    public void changeEventStatus(View view) {
        Button button = findViewById(R.id.eventStatusButton);
        String buttonText = button.getText().toString();
        if (buttonText.toLowerCase().equals("joined")) {
            button.setText(getResources().getText(R.string.join_txt));
            button.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_rounded_8dp_black, null));
            button.setTextColor(getResources().getColor(R.color.colorAccent));
        } else {
            button.setText(getResources().getText(R.string.joined_txt));
            button.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.btn_rounded_8dp_light_gray, null));
            button.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
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

    private void prepareUsersData() {
        usersList.add(new User(1, "Brandom Wilson", "abc@domain.com", "password", new Role(false, true, false), "Running", "", 180, 85, 18));
        usersList.add(new User(2, "Nelsol Cooper", "abc@domain.com", "password", new Role(false, true, false), "Running", "", 180, 85, 18));
        usersList.add(new User(3, "Mihai Icon", "abc@domain.com", "password", new Role(false, true, false), "Running", "", 180, 85, 18));
        usersList.add(new User(4, "Ron Shit", "abc@domain.com", "password", new Role(false, true, false), "Running", "", 180, 85, 18));
        userAdapter.notifyDataSetChanged();
    }
}