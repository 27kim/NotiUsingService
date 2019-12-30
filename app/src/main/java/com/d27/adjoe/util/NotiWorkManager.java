package com.d27.adjoe.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.d27.adjoe.service.SingleNotificationService;

public class NotiWorkManager extends Worker {

    public NotiWorkManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        SingleNotificationService.start(getApplicationContext());
        return Result.success();
    }
}
