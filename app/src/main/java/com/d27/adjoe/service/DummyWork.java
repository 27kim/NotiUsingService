package com.d27.adjoe.service;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.d27.adjoe.receiver.UserPresentReceiver;

import static com.d27.adjoe.App.TAG;

/**
 * worker class to register broadcast receiver for ACTION_USER_PRESENT
 * */

public class DummyWork extends Worker {

    public DummyWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "DummyWork - doWork() .. starting ACTION_USER_PRESENT Receiver ");

        UserPresentReceiver receiver = new UserPresentReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_USER_PRESENT);
        getApplicationContext().registerReceiver(receiver, intentFilter);
        //App sometimes can't receive broadcast when using success
        //retry re-register broadcast receiver prevent receiver from missing
        return Result.retry();
        //App sometimes can't receive broadcast using success
        //return Result.success();
    }
}
