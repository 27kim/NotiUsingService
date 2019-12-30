package com.d27.adjoe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.d27.adjoe.service.SingleNotificationService;

import static com.d27.adjoe.App.TAG;

public class UserPresentReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(Intent.ACTION_USER_PRESENT.equals(intent.getAction())){
            Intent singleNotificationService = new Intent(context, SingleNotificationService.class);
            Log.d(TAG, "onReceive ACTION_USER_PRESENT");

            if (Intent.ACTION_USER_PRESENT.equals(intent.getAction())) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Log.d(TAG, "startForegroundService");
                    context.startForegroundService(singleNotificationService);
                } else {
                    context.startService(singleNotificationService);
                }
            }
        }
    }
}
