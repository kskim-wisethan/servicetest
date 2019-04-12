package com.wisethan.myautomessagetest;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

public class MyMessengerService extends Service {
    private static final String TAG = "MyMessengerService";

    static final int MSG_SAY_HELLO = 1;

    Messenger mMessenger;

    public MyMessengerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind() called");
        mMessenger = new Messenger(new IncomingHandler(this));
        return mMessenger.getBinder();
    }

    static class IncomingHandler extends Handler {
        private Context applicationContext;

        IncomingHandler(Context context) {
            applicationContext = context.getApplicationContext();
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SAY_HELLO:
                    Log.d(TAG, "handleMessage() called");
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
}
