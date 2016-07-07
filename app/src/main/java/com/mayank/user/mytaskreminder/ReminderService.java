package com.mayank.user.mytaskreminder;

/**
 * Created by user on 07-07-2016.
 */

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class ReminderService extends WakeReminderIntentService {

    public ReminderService() {
        super("ReminderService");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    void doReminderWork(Intent intent) {
        Log.d("ReminderService", "Doing work.");
        Long rowId = intent.getExtras().getLong(RemindersDbAdapter.KEY_ROWID);

        NotificationManager mgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        /******
        note.setLatestEventInfo(this, getString(R.string.notify_new_task_title), getString(R.string.notify_new_task_message), pi);
        note.defaults |= Notification.DEFAULT_SOUND;
        note.flags |= Notification.FLAG_AUTO_CANCEL;
        ***/

        Intent notificationIntent = new Intent(this, ReminderEditActivity.class);
        notificationIntent.putExtra(RemindersDbAdapter.KEY_ROWID, rowId);

        PendingIntent pi = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_ONE_SHOT);
        // build notification
        // the addAction re-use the same intent to keep the example short
        Notification note  = new Notification.Builder(this)
                .setContentTitle(getString(R.string.notify_new_task_title))
                .setContentText( getString(R.string.notify_new_task_message))
                .setSmallIcon(R.drawable.icon)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .addAction(R.drawable.icon, "Call",pi)
                .addAction(R.drawable.icon, "More",pi)
                .addAction(R.drawable.icon, "And more", pi).build();


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        int id = (int)((long)rowId);
        notificationManager.notify(id, note);

    }
}
