package com.d27.adjoe.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Chronometer;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.d27.adjoe.App;
import com.d27.adjoe.MainActivity;
import com.d27.adjoe.R;
import static com.d27.adjoe.App.TAG;

import java.util.concurrent.atomic.AtomicInteger;

import static com.d27.adjoe.App.CHANNEL_ID;

/**
 * service to create notification
 * */

public class SingleNotificationService extends Service {

    public static final int NOTIFICATION_ID = 0xAA;
    NotificationManager notificationManager;
    static final AtomicInteger ID_ISSUER = new AtomicInteger(0xA1);


    public static void start(Context context) {
        Intent starter = new Intent(context, SingleNotificationService.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(starter);
        } else {
            context.startService(starter);
        }
    }

    PendingIntent pendingIntent;
    Intent notificationIntent;
    static final int FOREGROUND_SERVICE_ID = 0xB1;

    public SingleNotificationService() {
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "NotificationService - onCreate");
        super.onCreate();

        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationIntent = new Intent(this, MainActivity.class);

        pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        startForeground(FOREGROUND_SERVICE_ID, getNotification());
    }

    private Notification getNotification() {
        Log.d(TAG, "getNotification time stamp : " + App.getTimestamp());

        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(getString(R.string.NOTI_TITLE))
                .setContentText(App.getTimestamp())
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .build();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        notificationManager.notify(SingleNotificationService.NOTIFICATION_ID, getNotification());
        //to create each separate notification
//        notificationManager.notify(ID_ISSUER.incrementAndGet(), getNotification());
        return START_NOT_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void stop(Context context) {
        Intent stopper = new Intent(context, SingleNotificationService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.stopService(stopper);
        } else {
            context.stopService(stopper);

        }
    }
}
