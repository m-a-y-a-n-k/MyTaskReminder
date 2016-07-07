package com.mayank.user.mytaskreminder;

/**
 * Created by user on 07-07-2016.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.util.Log;

public class OnAlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "mytaskreminder.TAG";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Received wake up from alarm manager.");

        // Retrieving row id on recieve
        long rowid = intent.getExtras().getLong(RemindersDbAdapter.KEY_ROWID);

        // Acquire a static lock
        WakeReminderIntentService.acquireStaticLock(context);

        // Start a Reminder Service
        Intent i = new Intent(context, ReminderService.class);
        i.putExtra(RemindersDbAdapter.KEY_ROWID, rowid);
        context.startService(i);

    }
}

