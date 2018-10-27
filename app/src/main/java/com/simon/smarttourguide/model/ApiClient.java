package com.simon.smarttourguide.model;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import static com.simon.smarttourguide.BuildConfig.DEBUG;


public class ApiClient {

    public static String BASE_URL = "";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {

        if (DEBUG) {
            BASE_URL = "https://testapi.grabbngo.com/storeadmin/";

        } else {
            BASE_URL = "https://api.grabbngo.com/storeadmin/";
        }
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
