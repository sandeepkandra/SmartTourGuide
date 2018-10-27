package com.simon.smarttourguide.utility;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.multidex.MultiDexApplication;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.simon.smarttourguide.model.ApiClient;
import com.simon.smarttourguide.model.ApiInterface;


import java.util.ArrayList;
import java.util.HashMap;






public class MyApp extends MultiDexApplication {


    public static final String PREFS_NAME = "MyPrefsFile";
    static public SharedPreferences sharedPreferences;
    static public String projectToken = "";
    private static Context mContext;
    public static MyApp instace;
    public static ApiInterface apiService;
    public static RequestQueue reqstQ;
    public static boolean isInternetPresent =false;

    public static String logUrl="";
    public static  float density;

    public  static String url="https://app.grabbngo.com/";


    @Override
    public void onCreate() {
        super.onCreate();

        apiService = ApiClient.getClient().create(ApiInterface.class);
        reqstQ = Volley.newRequestQueue(getApplicationContext());
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

    }
}
