package com.d27.adjoe.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.d27.adjoe.service.NotificationService;
import com.d27.adjoe.service.ReceiverService;

public class UserPresentReceiver extends BroadcastReceiver {

    public static final String USER_PRESENT_RECEIVER = UserPresentReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
            Log.d(USER_PRESENT_RECEIVER, ".. ACTION_BOOT_COMPLETED 0000");
            startServiceByAlarm(context);
        }
    }

    private void startServiceByAlarm(Context context)
    {
        // Get alarm manager.
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        // Create intent to invoke the background service.
        Intent intent = new Intent(context, ReceiverService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        long startTime = System.currentTimeMillis();
        long intervalTime = 60*1000;

        String message = "Start service use repeat alarm. ";

        Log.d(USER_PRESENT_RECEIVER, message);

        // Create repeat alarm.
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startTime, intervalTime, pendingIntent);
    }
}
