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
import androidx.work.Constraints;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.d27.adjoe.NotiWorkManager;
import com.d27.adjoe.R;
import com.d27.adjoe.service.NotificationService;
import com.d27.adjoe.util.AlarmUtils;

import java.util.concurrent.TimeUnit;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class NotiFragment extends Fragment {

    public static final String TAG = NotiFragment.class.getSimpleName();

    Button mButton1;
    Button mButton2;
    Button mButton3;
    Button mButton4;
    Button mButton5;
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

        mButton1.setText("using AlarmManager");
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlarmUtils().getInstance().startFiveSecondAlarm(getContext());
            }
        });
        mButton2.setText("using WorkManager - 15minutes min interval");
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constraints constraints = new Constraints.Builder()
                        .setRequiresCharging(true)
                        .build();
                //minimum allowed value 15 minutes
                PeriodicWorkRequest.Builder builder = new PeriodicWorkRequest
                        .Builder(NotiWorkManager.class, 15, TimeUnit.SECONDS)
                        .setConstraints(constraints)
                        .addTag(getString(R.string.WORKMANAGER_TAG))
                        ;
                PeriodicWorkRequest workRequest = builder.build();
                WorkManager.getInstance().enqueue(workRequest);
            }
        });
        mButton3.setText("using job scheduler");
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobScheduler jobScheduler = (JobScheduler) getContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
                jobScheduler.schedule(new JobInfo
                        .Builder(0XC1, new ComponentName(getContext(), NotificationService.class))
                        .setMinimumLatency(15 * 60 * 1000)
                        .build());
            }
        });
        mButton4.setText("NotificationService.start(getContext())");
        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationService.start(getContext());
            }
        });
        mButton5.setText("NotificationService.stop");
        mButton5.setOnClickListener(new View.OnClickListener() {
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
