package com.wisethan.myautomessagetest;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {
    private static final String TAG = "IntentService";

    public static final String ACTION_FOO = "com.wisethan.myautomessagetest.action.FOO";
    public static final String ACTION_BAZ = "com.wisethan.myautomessagetest.action.BAZ";

    public static final String EXTRA_PARAM1 = "com.wisethan.myautomessagetest.extra.PARAM1";
    public static final String EXTRA_PARAM2 = "com.wisethan.myautomessagetest.extra.PARAM2";

    public MyIntentService() {
        super("MyIntentService");
    }

    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    private void handleActionFoo(String param1, String param2) {
        Log.i(TAG, "handleActionFoo");

        //throw new UnsupportedOperationException("Not yet implemented");
    }

    private void handleActionBaz(String param1, String param2) {
        Log.i(TAG, "handleActionBaz");

        String status = "status value";
        Intent localIntent = new Intent(Constants.BROADCAST_ACTION).putExtra(Constants.EXTENDED_DATA_STATUS, status);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);

        //throw new UnsupportedOperationException("Not yet implemented");
    }
}
