package com.simon.smarttourguide.utility;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by sandy on 5/5/17.
 */

public class BootBroadcastReceiver extends BroadcastReceiver {
    private BroadcastReceiver smsReceiver;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("GNG", "BootBroadcast");
        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {

        }
    }
}
