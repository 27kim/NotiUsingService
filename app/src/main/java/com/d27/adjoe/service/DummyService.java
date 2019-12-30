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

import com.d27.adjoe.activity.MainActivity;
import com.d27.adjoe.R;
import com.d27.adjoe.receiver.UserPresentReceiver;

import static com.d27.adjoe.App.TAG;
import static com.d27.adjoe.App.CHANNEL_ID;

/**
 * dummy service to crate notification for foreground service
 * to register broadcast receiver for ACTION_USER_PRESENT
 * */
public class DummyService extends Service {


    public static final int DUMMY_SERVICE_ID = 220;
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
        Log.d(TAG, "registerReceiver from DummyService");

        notificationIntent = new Intent(this, MainActivity.class);

        pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        startForeground(DUMMY_SERVICE_ID, getNotification());

        UserPresentReceiver receiver = new UserPresentReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(receiver, intentFilter);
    }

    private Notification getNotification() {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(getBaseContext().getString(R.string.adjoe))
                .setContentText(getBaseContext().getString(R.string.noti_context))
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .build();
    }
}
