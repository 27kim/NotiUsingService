package com.d27.adjoe.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.d27.adjoe.MainActivity;
import com.d27.adjoe.R;
import com.d27.adjoe.receiver.UserPresentReceiver;

import static com.d27.adjoe.App.TAG;
import static com.d27.adjoe.App.CHANNEL_ID;
import static com.d27.adjoe.App.getTimestamp;

/**
 * dummy service to crate notification for foreground service
 * to register broadcast receiver for ACTION_USER_PRESENT
 * */
public class DummyService extends Service {


    PendingIntent pendingIntent;
    Intent notificationIntent;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "DummyService time stamp : " + getTimestamp());

        notificationIntent = new Intent(this, MainActivity.class);

        pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        startForeground(220, getNotification());

        UserPresentReceiver receiver = new UserPresentReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(receiver, intentFilter);
    }

    private Notification getNotification() {
        Log.i(TAG, "DummyService time stamp : " + getTimestamp());
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Adjoe")
                .setContentText("Service is running")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .build();
    }
}
