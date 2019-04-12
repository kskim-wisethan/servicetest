package com.wisethan.myautomessagetest;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;


public class MyJobIntentService extends JobIntentService {
    private static final String TAG = "MyJobIntentService";

    public static final int JOB_ID = 1000;

    static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, MyJobIntentService.class, JOB_ID, work);
    }

    public MyJobIntentService() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestory() called");
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.i(TAG, "Executing work: " + intent);
        String label = intent.getStringExtra("label");
        if (label == null) {
            label = intent.toString();
        }
        for (int i = 0; i < 5; i++) {
            Log.i(TAG, "Running service " + (i + 1) + "/5 @ " + SystemClock.elapsedRealtime());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        Log.i("SimpleJobIntentService", "Completed service @ " + SystemClock.elapsedRealtime());
    }
}
