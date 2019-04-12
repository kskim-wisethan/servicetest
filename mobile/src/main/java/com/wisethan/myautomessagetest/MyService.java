package com.wisethan.myautomessagetest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MyService extends Service {
    private static final String TAG = "MyService";

    private String mInput;
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;

    private final IBinder mBinder = new LocalBinder();

    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "handleMessage() called");

            Intent showIntent = new Intent(getApplicationContext(), MainActivity.class);
            showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            showIntent.putExtra("command", "show");
            showIntent.putExtra("input", mInput);
            startActivity(showIntent);

            //sendBroadcast(showIntent);

            // Normally we would do some work here, like download a file.
            // For our sample, we just sleep for 5 seconds.
            try {
                Log.i(TAG, "handle message");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // Restore interrupt status.
                Thread.currentThread().interrupt();
            }
            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
            stopSelf(msg.arg1);
        }
    }

    public class LocalBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind() called");

        String command = intent.getStringExtra("command");
        String input = intent.getStringExtra("input");
        mInput = input;

        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnBind() called");
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() called");

        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
        HandlerThread thread = new HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() called");

        if (intent == null) {
            return Service.START_STICKY;
        } else {
            //processCommand(intent);
            String command = intent.getStringExtra("command");
            String input = intent.getStringExtra("input");
            mInput = input;

            // For each start request, send a message to start a job and deliver the
            // start ID so we know which request we're stopping when we finish the job
            Message msg = mServiceHandler.obtainMessage();
            msg.arg1 = startId;
            mServiceHandler.sendMessage(msg);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void processCommand(Intent intent) {
        String command = intent.getStringExtra("command");
        String input = intent.getStringExtra("input");

        for (int i = 0; i < 10; i++) {
            Log.d(TAG, "command: " + command + ", input: " + input);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendLocalBroadcast() {
        String status = "status value";
        Intent localIntent = new Intent(Constants.BROADCAST_ACTION).putExtra(Constants.EXTENDED_DATA_STATUS, status);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }

    public String getInput() {
        return mInput;
    }
}
