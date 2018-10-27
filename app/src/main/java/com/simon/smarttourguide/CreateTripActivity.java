package com.simon.smarttourguide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.map.BaseMarkerBalloon;
import com.tomtom.online.sdk.map.MarkerBuilder;
import com.tomtom.online.sdk.map.Route;
import com.tomtom.online.sdk.search.data.alongroute.AlongRouteSearchQueryBuilder;
import com.tomtom.online.sdk.search.data.alongroute.AlongRouteSearchResponse;
import com.tomtom.online.sdk.search.data.alongroute.AlongRouteSearchResult;
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchQueryBuilder;

import java.util.List;
import java.util.Optional;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CreateTripActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnSearch;
    private EditText editTextPois;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);
        btnSearch = findViewById(R.id.btn_main_poisearch);
        editTextPois = findViewById(R.id.edittext_main_poisearch);


//        View.OnClickListener searchButtonListener = getSearchButtonListener();
//        btnSearch.setOnClickListener(searchButtonListener);
    }

    @Override
    public void onClick(View v) {
        // do something when the button is clicked
        // Yes we will handle click here but which button clicked??? We don't know

        // So we will make
        switch (v.getId() /*to get clicked view id**/) {
            case R.id.btn_main_poisearch:

                // do something when the corky is clicked

                break;

            default:
                break;
        }
    }

    /*@NonNull
    private View.OnClickListener getSearchButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSearchClick(v);
            }

            private void handleSearchClick(View v) {
                if (isRouteSet()) {
                    Optional<CharSequence> description = Optional.fromNullable(v.getContentDescription());

                    if (description.isPresent()) {
                        editTextPois.setText(description.get());
                        v.setSelected(true);
                    }
                    if (isWayPointPositionSet()) {
                        tomtomMap.clear();
                        drawRoute(departurePosition, destinationPosition);
                    }
                    String textToSearch = editTextPois.getText().toString();
                    if (!textToSearch.isEmpty()) {
                        tomtomMap.removeMarkers();
                        searchAlongTheRoute(route, textToSearch);
                    }
                }
            }

            private boolean isRouteSet() {
                return route != null;
            }

            private boolean isWayPointPositionSet() {
                return wayPointPosition != null;
            }
            private void searchAlongTheRoute(Route route, final String textToSearch) {
                final Integer MAX_DETOUR_TIME = 1000;
                final Integer QUERY_LIMIT = 10;

                searchApi.alongRouteSearch(new AlongRouteSearchQueryBuilder(textToSearch, route.getCoordinates(), MAX_DETOUR_TIME).withLimit(QUERY_LIMIT))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DisposableSingleObserver<AlongRouteSearchResponse>() {
                            @Override
                            public void onSuccess(AlongRouteSearchResponse response) {
                                displaySearchResults(response.getResults());
                            }

                            private void displaySearchResults(List<AlongRouteSearchResult> results) {
                                if (!results.isEmpty()) {
                                    for (AlongRouteSearchResult result : results) {
                                        createAndDisplayCustomMarker(result.getPosition(), result);
                                    }
                                    tomtomMap.zoomToAllMarkers();
                                } else {
//                                    Toast.makeText(CreateTripActivity.this, "ha", textToSearch, Toast.LENGTH_LONG).show();
                                }
                            }

                            private void createAndDisplayCustomMarker(LatLng position, AlongRouteSearchResult result) {
                                String address = result.getAddress().getFreeformAddress();
                                String poiName = result.getPoi().getName();

                                BaseMarkerBalloon markerBalloonData = new BaseMarkerBalloon();
                                markerBalloonData.addProperty(getString(R.string.poi_name_key), poiName);
                                markerBalloonData.addProperty(getString(R.string.address_key), address);

                                MarkerBuilder markerBuilder = new MarkerBuilder(position)
                                        .markerBalloon(markerBalloonData)
                                        .shouldCluster(true);
                                tomtomMap.addMarker(markerBuilder);
                            }

                            @Override
                            public void onError(Throwable e) {
                                handleApiError(e);
                            }
                        });
            }
        };
    }*/
}
