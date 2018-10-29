package com.simon.smarttourguide.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import com.simon.smarttourguide.R;
import com.simon.smarttourguide.adapter.PlaceArrayAdapter;
import com.tomtom.online.sdk.search.OnlineSearchApi;
import com.tomtom.online.sdk.search.SearchApi;
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchQuery;
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchQueryBuilder;
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchResponse;
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchResult;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.reactivex.observers.DisposableSingleObserver;

public class CreateTripActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {
    private static final String LOG_TAG = "MainActivity";
    private static final int GOOGLE_API_CLIENT_ID = 0;

    private TextView mNameTextView;
    private TextView mAddressTextView;
    private TextView mIdTextView;
    private TextView mPhoneTextView;
    private TextView mWebTextView;
    private TextView mAttTextView;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));

    private ImageButton btnSearch;
    private EditText editTextStartDate, editTextStartTime, editTextEndDate, editTextEndTime, editTextNOC;
    private AutoCompleteTextView editTextPois;
    Calendar myCalendar = Calendar.getInstance();

    Button btnAdd, btnSubmit;
    private Spinner mAutocompleteTextView1, mAutocompleteTextView2, mAutocompleteTextView3, mAutocompleteTextView4, mAutocompleteTextView5;

    private static final String[] Address1 = new String[]{
            "Select City",
            "Belgium", "France", "Italy", "Germany", "Spain"
    };

    private boolean a1 = false, a2 = false, a3 = false, a4 = false, a5 = false;
    private int i = 1;
    LinearLayout linearLayout1, linearLayout2, linearLayout3, linearLayout4, linearLayout5;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        editTextNOC = findViewById(R.id.editTextNOC);

        mAutocompleteTextView1 = (Spinner) findViewById(R.id
                .autoCompleteTextView1);
        mAutocompleteTextView2 = (Spinner) findViewById(R.id
                .autoCompleteTextView2);
        mAutocompleteTextView3 = (Spinner) findViewById(R.id
                .autoCompleteTextView3);
        mAutocompleteTextView4 = (Spinner) findViewById(R.id
                .autoCompleteTextView4);
        mAutocompleteTextView5 = (Spinner) findViewById(R.id
                .autoCompleteTextView5);

        linearLayout1 = findViewById(R.id.linear1);
        linearLayout2 = findViewById(R.id.linear2);
        linearLayout3 = findViewById(R.id.linear3);
        linearLayout4 = findViewById(R.id.linear4);
        linearLayout5 = findViewById(R.id.linear5);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Address1);
        mAutocompleteTextView1.setAdapter(adapter1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, Address1);
        mAutocompleteTextView2.setAdapter(adapter2);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, Address1);
        mAutocompleteTextView3.setAdapter(adapter3);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, Address1);
        mAutocompleteTextView4.setAdapter(adapter4);
        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, Address1);
        mAutocompleteTextView5.setAdapter(adapter5);


        mAutocompleteTextView1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        // PlacesLoc Api
//               btnSearch = findViewById(R.id.btn_main_poisearch);
//        btnSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getAddress();
//            }
//        });

//        mGoogleApiClient = new GoogleApiClient.Builder(CreateTripActivity.this)
//                .addApi(PlacesLoc.GEO_DATA_API)
//                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
//                .addConnectionCallbacks(this)
//                .build();
//        mAutocompleteTextView.setThreshold(3);
//        mAutocompleteTextView.setAdapter(adapter);

//        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);
//
//        mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);
//        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
//                BOUNDS_MOUNTAIN_VIEW, null);
//        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);

        editTextStartDate = (EditText) findViewById(R.id.editTextStartDate);
        editTextStartTime = findViewById(R.id.editTextStartTime);
        editTextEndDate = findViewById(R.id.editTextEndDate);
        editTextEndTime = findViewById(R.id.editTextEndTime/**/);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        editTextStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateTripActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                editTextStartDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        editTextStartTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateTripActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                editTextStartTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });
        editTextEndDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateTripActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                editTextEndDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        editTextEndTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateTripActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                editTextEndTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });


    }


    @Override
    public void onClick(View v) {
        // do something when the button is clicked
        // Yes we will handle click here but which button clicked??? We don't know

        // So we will make
        switch (v.getId() /*to get clicked view id**/) {
            case R.id.btnAdd:

                // do something when the corky is clicked\

                i = i + 1;
                if (i <= 5) {
                    if (i == 2) {
                        linearLayout2.setVisibility(View.VISIBLE);
                    } else if (i == 3) {
                        linearLayout3.setVisibility(View.VISIBLE);
                    } else if (i == 4) {
                        linearLayout4.setVisibility(View.VISIBLE);
                    } else if (i == 5) {
                        linearLayout5.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(this, "Maximum location is added", Toast.LENGTH_LONG).show();
                }


                break;
            case R.id.btnSubmit:

                editTextStartDate.setError(null);
                editTextStartTime.setError(null);
                editTextEndTime.setError(null);
                editTextEndDate.setError(null);
                editTextNOC.setError(null);
                if (editTextStartDate.getText().toString().trim().equals("")) {

                    editTextStartDate.setFocusable(true);
                    editTextStartDate.requestFocus();

                    String estring = "Select start date";
                    ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.RED);
                    SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
                    ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
                    editTextStartDate.setError(ssbuilder);
                    return;
                }


                if (editTextStartDate.getText().toString().startsWith(" ")) {
                    String estring = "Space is not allowed in first character";
                    ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.RED);
                    SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
                    ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
                    editTextStartDate.setError(ssbuilder);
                    editTextStartDate.requestFocus();
                    return;
                } else {
//                    passcode = editTextStartDate.getText().toString();
                }

                if (editTextStartTime.getText().toString().trim().equals("")) {

                    editTextStartTime.setFocusable(true);
                    editTextStartTime.requestFocus();

                    String estring = "Select start time";
                    ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.RED);
                    SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
                    ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
                    editTextStartTime.setError(ssbuilder);
                    return;
                }


                if (editTextEndDate.getText().toString().trim().equals("")) {

                    editTextEndDate.setFocusable(true);
                    editTextEndDate.requestFocus();

                    String estring = "Select end date";
                    ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.RED);
                    SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
                    ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
                    editTextEndDate.setError(ssbuilder);
                    return;
                }

                if (editTextEndTime.getText().toString().trim().equals("")) {

                    editTextEndTime.setFocusable(true);
                    editTextEndTime.requestFocus();

                    String estring = "Select end time";
                    ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.RED);
                    SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
                    ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
                    editTextEndTime.setError(ssbuilder);
                    return;
                }
                if (editTextNOC.getText().toString().trim().equals("")) {

                    editTextNOC.setFocusable(true);
                    editTextNOC.requestFocus();

                    String estring = "Enter Number of cars";
                    ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.RED);
                    SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
                    ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
                    editTextNOC.setError(ssbuilder);
                    return;
                }
                Intent intent=new Intent(this,PlacesActivity.class);
                startActivity(intent);

                break;

            default:
                break;
        }
    }


    public class AutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {

        private ArrayList<String> fullList;
        private ArrayList<String> mOriginalValues;
        private ArrayFilter mFilter;

        public AutoCompleteAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {

            super(context, resource, textViewResourceId, objects);
            fullList = (ArrayList<String>) objects;
            mOriginalValues = new ArrayList<String>(fullList);

        }

        @Override
        public int getCount() {
            return fullList.size();
        }

        @Override
        public String getItem(int position) {
            return fullList.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return super.getView(position, convertView, parent);
        }

        @Override
        public Filter getFilter() {
            if (mFilter == null) {
                mFilter = new ArrayFilter();
            }
            return mFilter;
        }


        private class ArrayFilter extends Filter {
            private Object lock;

            @Override
            protected FilterResults performFiltering(CharSequence prefix) {
                FilterResults results = new FilterResults();

                if (mOriginalValues == null) {
                    synchronized (lock) {
                        mOriginalValues = new ArrayList<String>(fullList);
                    }
                }

                if (prefix == null || prefix.length() == 0) {
                    synchronized (lock) {
                        ArrayList<String> list = new ArrayList<String>(mOriginalValues);
                        results.values = list;
                        results.count = list.size();
                    }
                } else {
                    final String prefixString = prefix.toString().toLowerCase();

                    ArrayList<String> values = mOriginalValues;
                    int count = values.size();

                    ArrayList<String> newValues = new ArrayList<String>(count);

                    for (int i = 0; i < count; i++) {
                        String item = values.get(i);
                        if (item.toLowerCase().contains(prefixString)) {
                            newValues.add(item);
                        }

                    }

                    results.values = newValues;
                    results.count = newValues.size();
                }

                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if (results.values != null) {
                    fullList = (ArrayList<String>) results.values;
                } else {
                    fullList = new ArrayList<String>();
                }
                if (results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        }
    }

//    private void getAddress(){
//        FuzzySearchQuery fsq= FuzzySearchQueryBuilder.create(editTextPois.getText().toString()).build();
//        SearchApi searchApi = OnlineSearchApi.create(CreateTripActivity.this);
//        searchApi.search(fsq)
//
//                .subscribe(new DisposableSingleObserver<FuzzySearchResponse>() {
//                    @Override
//                    public void onSuccess(FuzzySearchResponse fuzzySearchResponse) {
//                        Log.e("result",fuzzySearchResponse.getResults().toString());
//
//                        JSONObject songsObject = json.getJSONObject("songs");
//                        JSONArray songsArray = songsObject.toJSONArray();
//
//                        Toast.makeText(CreateTripActivity.this, fuzzySearchResponse.getResults().toString(), Toast.LENGTH_LONG).show();
//                        JsonObject jsonObject=new JsonObject(fuzzySearchResponse.getResults().toString());
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
////
//                    }
//                });
//    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editTextStartDate.setText(sdf.format(myCalendar.getTime()));
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(LOG_TAG, "Selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            Log.i(LOG_TAG, "Fetching details for ID: " + item.placeId);
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e(LOG_TAG, "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            // Selecting the first object buffer.
            final Place place = places.get(0);
            CharSequence attributions = places.getAttributions();

//            mAddressTextView.setText(Html.fromHtml(place.getAddress() + ""));
//            mIdTextView.setText(Html.fromHtml(place.getId() + ""));
//            mPhoneTextView.setText(Html.fromHtml(place.getPhoneNumber() + ""));
//            mWebTextView.setText(place.getWebsiteUri() + "");
//            if (attributions != null) {
//                mAttTextView.setText(Html.fromHtml(attributions.toString()));
//            }
        }
    };

    @Override
    public void onConnected(Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(LOG_TAG, "Google PlacesLoc API connected.");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "Google PlacesLoc API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(this,
                "Google PlacesLoc API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(LOG_TAG, "Google PlacesLoc API connection suspended.");
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
