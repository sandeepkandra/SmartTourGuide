package com.simon.smarttourguide.activities;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.simon.smarttourguide.R;
import com.tomtom.online.sdk.common.location.LatLng;
import com.tomtom.online.sdk.map.MapFragment;
import com.tomtom.online.sdk.map.OnMapReadyCallback;
import com.tomtom.online.sdk.map.Route;
import com.tomtom.online.sdk.map.RouteBuilder;
import com.tomtom.online.sdk.map.TomtomMap;
import com.tomtom.online.sdk.map.TomtomMapCallback;
import com.tomtom.online.sdk.routing.OnlineRoutingApi;
import com.tomtom.online.sdk.routing.RoutingApi;
import com.tomtom.online.sdk.routing.data.FullRoute;
import com.tomtom.online.sdk.routing.data.InstructionsType;
import com.tomtom.online.sdk.routing.data.Report;
import com.tomtom.online.sdk.routing.data.RouteQuery;
import com.tomtom.online.sdk.routing.data.RouteQueryBuilder;
import com.tomtom.online.sdk.routing.data.RouteResponse;
import com.tomtom.online.sdk.routing.data.TravelMode;

import java.util.ArrayList;
import java.util.List;


import io.reactivex.disposables.Disposable;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
        TomtomMapCallback.OnMapLongClickListener {
    private TomtomMap tomtomMap;
    private RoutingApi routePlannerAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initTomTomServices();
        initUIViews();
        setupUIViewListeners();
        routePlannerAPI = OnlineRoutingApi.create(MapActivity.this);
    }

    @Override
    public void onMapReady(@NonNull TomtomMap tomtomMap) {
        this.tomtomMap = tomtomMap;
        this.tomtomMap.setMyLocationEnabled(true);
        this.tomtomMap.addOnMapLongClickListener(this);
        this.tomtomMap.centerOnMyLocation();
//        this.tomtomMap.getMarkerSettings().setMarkersClustering(true);

        LatLng[] wayPointsArray = new LatLng[5];
        try {
            wayPointsArray[0] = new LatLng(13.003385, 77.660617);
            wayPointsArray[1] = new LatLng(12.2343, 77.23232);

            LatLng latLngOrgin = new LatLng(12.9270341, 77.5810651);

            Location location = tomtomMap.getUserLocation();

            LatLng latLngDest = new LatLng(location.getLatitude(), location.getLongitude());

            RouteQuery routeQuery = RouteQueryBuilder.create(latLngDest, latLngOrgin)
//              .withWayPoints(wayPointsArray)
//                .withComputeBestOrder(computeBestOrder)
                    .withConsiderTraffic(false).build();

            RouteResponse response = routePlannerAPI.planRoute(routeQuery).blockingGet();
            Log.e("res", "" + response.getRoutes());
            List<FullRoute> routes = response.getRoutes();
            FullRoute route = routes.iterator().next();
            RouteBuilder routeBuilder = new RouteBuilder(route.getCoordinates());
            Route mapRoute = tomtomMap.addRoute(routeBuilder);
        }catch (Exception e){

        }
           /*     .subscribe(response -> {
                    Log.e("response", "" + response.hasResults());
                    Log.e("response1", "" + response.getRoutes());
                }, error -> {});
*/

//        RouteQuery routeQuery = RouteQueryBuilder.create(latLngOrgin, latLngDest)
//                .withWayPoints(wayPointsArray)
////                .withComputeBestOrder(computeBestOrder)
//                .withConsiderTraffic(true).build();
//
//
//
//        List latLngList= new ArrayList();
//        latLngList.add(latLngOrgin);
//        latLngList.add(latLngDest);
//
//        RouteBuilder routeBuilder = new RouteBuilder(latLngList);
//
//
//        final Route mapRoute = tomtomMap.addRoute(routeBuilder);
//        this.tomtomMap.addRoute(routeBuilder);
//        RouteQuery queryBuilder = RouteQueryBuilder.create(latLngOrgin, latLngDest)
//                .withMaxAlternatives(0)
//                .withReport(Report.EFFECTIVE_SETTINGS)
//                .withInstructionsType(InstructionsType.TEXT)
//                .withTravelMode(TravelMode.CAR)
//                .withConsiderTraffic(true).build();

//        Location location = tomtomMap.getUserLocation();
//        LatLng latLng =new LatLng(location.getLatitude(),location.getLongitude());
//        MarkerBuilder markerBuilder = new MarkerBuilder(latLng)
//                .icon(Icon.Factory.fromResources(this, R.drawable.ic_favourites))
//                .markerBalloon(new SimpleMarkerBalloon("User"))
//                .tag("more information in tag").iconAnchor(MarkerAnchor.Bottom)
//                .decal(true); //By default is false
//        tomtomMap.addMarker(markerBuilder);
    }



    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {}

    private void initTomTomServices() {
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getAsyncMap(this);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        this.tomtomMap.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private void initUIViews() {}
    private void setupUIViewListeners() {}

}
