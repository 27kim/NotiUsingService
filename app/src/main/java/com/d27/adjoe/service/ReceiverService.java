package com.d27.adjoe.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import static com.d27.adjoe.App.TAG;

public class ReceiverService extends Service {
    private BroadcastReceiver receiver;
    private static final String ACTION = "android.intent.action.USER_PRESENT";

    @Override
    public void onCreate() {
        Log.d(TAG, "ReceiverService - onCreate");
        final IntentFilter theFilter = new IntentFilter();
        theFilter.addAction(ACTION);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "ReceiverService - onReceive");
                NotificationService.start(context);
            }
        };
        super.onCreate();
        registerReceiver(receiver , theFilter);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
