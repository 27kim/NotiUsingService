package com.d27.adjoe.fragment;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.d27.adjoe.NotiWorkManager;
import com.d27.adjoe.R;
import com.d27.adjoe.service.NotificationService;
import com.d27.adjoe.service.SingleNotificationService;
import com.d27.adjoe.util.AlarmUtils;

import static com.d27.adjoe.App.TAG;

import java.util.concurrent.TimeUnit;

/**
 * can't use both WorkManager and JobScheduler
 * WorkManager and JobScheduler needs at least 15 minutes for periodic job
 * */

public class NotiFragment extends Fragment {

    JobScheduler jobScheduler;


    Button mButton1;
    Button mButton2;
    Button mButton3;
    Button mButton4;
    Button mButton5;

    Button mButton1_2;
    Button mButton2_2;
    Button mButton3_2;
    Button mButton4_2;
    Button mButton5_2;
    public NotiFragment() {
        // Required empty public constructor
    }

    public static NotiFragment newInstance() {
        NotiFragment fragment = new NotiFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_noti, container, false);

        mButton1 = view.findViewById(R.id.btn1);
        mButton2 = view.findViewById(R.id.btn2);
        mButton3 = view.findViewById(R.id.btn3);
        mButton4 = view.findViewById(R.id.btn4);
        mButton5 = view.findViewById(R.id.btn5);
        mButton1_2 = view.findViewById(R.id.btn1_2);
        mButton2_2 = view.findViewById(R.id.btn2_2);
        mButton3_2 = view.findViewById(R.id.btn3_2);
        mButton4_2 = view.findViewById(R.id.btn4_2);
        mButton5_2 = view.findViewById(R.id.btn5_2);

        mButton1.setText("using AlarmManager");
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlarmUtils().getInstance().startFiveSecondAlarm(getContext());
            }
        });

        mButton1_2.setText("cancel alarm");
        mButton1_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlarmUtils().getInstance().stopAlarm();
            }
        });

        mButton2.setText("using WorkManager");
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //minimum allowed value 15 minutes
                PeriodicWorkRequest.Builder builder = new PeriodicWorkRequest
                        .Builder(NotiWorkManager.class, 15, TimeUnit.SECONDS)
                        .addTag(getString(R.string.WORKMANAGER_TAG));
                PeriodicWorkRequest workRequest = builder.build();
                WorkManager.getInstance().enqueue(workRequest);
            }
        });
        mButton2_2.setText("cancel WorkManager");
        mButton2_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkManager.getInstance().cancelAllWorkByTag(getString(R.string.WORKMANAGER_TAG));
            }
        });
        mButton3.setText("using job scheduler");
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "using job scheduler");
                jobScheduler = (JobScheduler) getContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
                jobScheduler.schedule(new JobInfo
                        .Builder(0XC1, new ComponentName(getContext(), SingleNotificationService.class))
                        .setPeriodic(TimeUnit.MINUTES.toMillis(15))
                        .build());

            }
        });
        mButton3_2.setText("cancel job scheduler");
        mButton3_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "cancel job scheduler");
                jobScheduler.cancelAll();

            }
        });
        mButton4.setText("Repeat Notification");
        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationService.start(getContext());
            }
        });
        mButton4_2.setText("Stop Repeat");
        mButton4_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationService.stop(getContext());
            }
        });
        mButton5.setText("Single Notification");
        mButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SingleNotificationService.start(getContext());
            }
        });
        mButton5_2.setText("stop Single ");
        mButton5_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationService.stop(getContext());
            }
        });
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        Log.i(TAG, "onDetach");
        WorkManager.getInstance().pruneWork();
        super.onDetach();
    }


}
