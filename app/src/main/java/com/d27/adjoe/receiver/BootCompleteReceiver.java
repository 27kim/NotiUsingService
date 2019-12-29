package com.d27.adjoe.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.util.Log;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.d27.adjoe.service.DummyService;
import com.d27.adjoe.service.DummyWork;
import com.d27.adjoe.service.NotificationService;
import com.d27.adjoe.service.ReceiverService;

import static com.d27.adjoe.App.TAG;

public class BootCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "BootCompleteReceiver - ACTION_BOOT_COMPLETED");

        /**
         * using dummy service to register
         * broadcast receiver for ACTION_USER_PRESENT
         * */
//        Intent dummyServiceIntent = new Intent();
//        ComponentName componentName = new ComponentName(context, DummyService.class);
//        dummyServiceIntent.setComponent(componentName);
//        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {D
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                context.startForegroundService(dummyServiceIntent);
//            } else {
//                context.startService(dummyServiceIntent);
//            }
//        }

        /**
         * using WorkManager to register
         * broadcast receiver for ACTION_USER_PRESENT
         * */
        OneTimeWorkRequest compressionWork =
                new OneTimeWorkRequest.Builder(DummyWork.class)
                        .build();
        WorkManager.getInstance().enqueue(compressionWork);
    }
}
