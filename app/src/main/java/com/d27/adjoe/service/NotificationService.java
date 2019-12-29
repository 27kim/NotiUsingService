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

import androidx.core.app.NotificationCompat;

import com.d27.adjoe.MainActivity;
import com.d27.adjoe.R;

import static com.d27.adjoe.App.TAG;
import static com.d27.adjoe.App.CHANNEL_ID;
import static com.d27.adjoe.App.getTimestamp;

public class NotificationService extends Service {
    private Chronometer mChronometer;
    PendingIntent pendingIntent;
    Intent notificationIntent;
    Thread thread;
    static final int FOREGROUND_SERVICE_ID = 0xB1;

    public NotificationService() {
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, NotificationService.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(starter);
        }else{
            context.startService(starter);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mChronometer = new Chronometer(this);
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.start();

        notificationIntent = new Intent(this, MainActivity.class);

        pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        startForeground(FOREGROUND_SERVICE_ID, getNotification());
    }

    private Notification getNotification() {
        Log.i(TAG, "time stamp : " + getTimestamp());
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(getString(R.string.NOTI_TITLE))
                .setContentText(getTimestamp())
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .build();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        thread = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    try {
                        Thread.sleep(5000);
                        startForeground(FOREGROUND_SERVICE_ID, getNotification());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        };

        thread.start();


        return START_NOT_STICKY;

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try{
            thread.interrupt();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.v(TAG, "in onUnbind");

        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.v(TAG, "in onRebind");
        super.onRebind(intent);
    }
    public static void stop(Context context) {
        Intent stopper = new Intent(context, NotificationService.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.stopService(stopper);
        }else{
            context.stopService(stopper);

        }
    }
}
