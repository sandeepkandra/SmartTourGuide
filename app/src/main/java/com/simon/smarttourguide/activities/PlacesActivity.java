package com.simon.smarttourguide.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.simon.smarttourguide.R;
import com.simon.smarttourguide.adapter.PlacesAdapter;
import com.simon.smarttourguide.model.PlacesLoc;

public class PlacesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPlaces;
    PlacesAdapter placesAdapter;
    ProgressDialog pd;
    PlacesLoc placesLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        recyclerViewPlaces = (RecyclerView) findViewById(R.id.recyclerViewPlaces);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewPlaces.setLayoutManager(layoutManager);
        recyclerViewPlaces.setNestedScrollingEnabled(false);

        pd = new ProgressDialog(PlacesActivity.this);
        pd.setCancelable(false);
        pd.setMessage("Loading, Please Wait...");
        pd.setCancelable(false);

        PlacesLoc placesLoc = new PlacesLoc();
        PlacesLoc.Place place = new PlacesLoc.Place();
        place.placeName = "Bangalore";
        place.placeName = "Chittoor";
        placesLoc.places.add(place);



        PlacesLoc.Place place1 = new PlacesLoc.Place();

        place.placeName = "Chittoor";
        placesLoc.places.add(place1);
        placesAdapter = new PlacesAdapter(placesLoc, getApplicationContext());

        //recyclerViewMenuItems.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewPlaces.setAdapter(placesAdapter);

//        GsonBuilder builder = new GsonBuilder();
//        Gson gson1 = builder.create();
//
//        History orderHistor = gson1.fromJson(response, History.class);
//        orderHistory.Orders = orderHistor.Orders;
    }
}
