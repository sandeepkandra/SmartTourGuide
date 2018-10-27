package com.simon.smarttourguide.utility;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static android.content.Context.WINDOW_SERVICE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by sandy on 16/08/16.
 */
public class CheckInternet extends BootBroadcastReceiver {


    private boolean isConnected = false;
    String message;
    private int count;
    private WindowManager mWindowManager;
    private TextView token;
    private View mView;

    Context context;

    private static final String LOG_TAG = "CheckNetworkStatus";
    private boolean isPopup;
    private  MediaPlayer mMediaPlayer;

    public CheckInternet(String message) {
        this.message=message;
    }


    @Override
    public void onReceive(final Context contxt, final Intent intent) {

        context =contxt;
        Log.v(LOG_TAG, "Receieved notification about network status");
        isNetworkAvailable(context,intent);

    }


    private boolean isNetworkAvailable(Context context,Intent intent) {
        ConnectivityManager connectivity = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        if(haveConnectedWifi || haveConnectedMobile)
        {
            Log.v(LOG_TAG, "You are  connected to Internet!");

        }else {

            try {
                Log.v(LOG_TAG, "You are not connected to Internet!");

                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                // Add the buttons

                builder.setMessage("You are not connected to Internet!");

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        dialog.dismiss();
                    }
                });
                android.support.v7.app.AlertDialog dialog = builder.create();
                dialog.show();


            }catch (Exception e)
            {
                Log.wtf("err1",e.toString());
            }
        }

        isConnected = false;
        return haveConnectedWifi || haveConnectedMobile;
        //return false;
    }



}
