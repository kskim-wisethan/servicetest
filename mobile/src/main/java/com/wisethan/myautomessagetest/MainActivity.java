package com.wisethan.myautomessagetest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    EditText mInputText;
    MyService mService;
    Messenger mMessenger;
    IRemoteService mIRemoteService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Service
        //  - Service / IntentService / JobIntentService
        //  - startService / bindService
        // Broadcast
        // AIDL

        // Threads / Handler
        // Looper

        // HTTP / TCP
        // Volley / JSON
        // Room / SQLite

        mInputText = findViewById(R.id.editText);
        Button buttonSend = findViewById(R.id.button);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = mInputText.getText().toString();

                /*
                Intent intent = new Intent(getApplicationContext(), MyService.class);
                intent.putExtra("command", "show");
                intent.putExtra("input", input);
                startService(intent);
                */
                if (mService != null) {
                    mService.sendLocalBroadcast();
                }

                if (mMessenger != null) {
                    sayHello(v);
                }
            }
        });

        /*
        Intent intent = new Intent(getApplicationContext(), MyService.class);
        intent.putExtra("command", "show");
        intent.putExtra("input", "Blank");
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
        */
        //bindService(new Intent(this, MyMessengerService.class), mServiceConnection, BIND_AUTO_CREATE);
        bindService(new Intent(this, MyRemoteService.class), mServiceConnection, BIND_AUTO_CREATE);

        Intent passedIntent = getIntent();
        processCommand(passedIntent);

        MyStateReceiver stateReceiver = new MyStateReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(stateReceiver, makeBroadcastIntentFilter());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        processCommand(intent);
        super.onNewIntent(intent);
    }

    private void processCommand(Intent intent) {
        String command = intent.getStringExtra("command");
        String input = intent.getStringExtra("input");

        Log.d(TAG, "command: " + command + ", input: " + input);
    }

    private class MyStateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String status = intent.getStringExtra(Constants.EXTENDED_DATA_STATUS);

            Log.d(TAG, "Received status: " + status);
        }
    }

    private static IntentFilter makeBroadcastIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.BROADCAST_ACTION);
        return intentFilter;
    }

    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            //mService = ((MyService.LocalBinder) service).getService();
            //mMessenger = new Messenger(service);
            mIRemoteService = IRemoteService.Stub.asInterface(service);

            //
            Log.d(TAG, "onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            //mService = null;
            //mMessenger = null;
            mIRemoteService = null;

            Log.d(TAG, "onServiceDisconnected");
        }
    };

    public void sayHello(View v) {
        if (mMessenger == null) {
            return;
        }

        Message msg = Message.obtain(null, MyMessengerService.MSG_SAY_HELLO, 0, 0);
        try {
            mMessenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
