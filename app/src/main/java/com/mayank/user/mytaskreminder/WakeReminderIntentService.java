package com.mayank.user.mytaskreminder;

/**
 * Created by user on 07-07-2016.
 */

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

public abstract class WakeReminderIntentService extends IntentService {
    abstract void doReminderWork(Intent intent);                                                 // Implementation in ReminderService class

    public static final String LOCK_NAME_STATIC="com.mayank.user.mytaskreminder.Static";        // STATIC_LOCK_NAME
    private static PowerManager.WakeLock lockStatic=null;

    // acquire a static lock on alarm recieve broadcast reciever
    public static void acquireStaticLock(Context context) {
        getLock(context).acquire();
    }

    // synchronized method to get System Service WakeLock from Power Manager
    synchronized private static PowerManager.WakeLock getLock(Context context) {
        if (lockStatic==null) {
            PowerManager mgr=(PowerManager)context.getSystemService(Context.POWER_SERVICE);
            lockStatic=mgr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    LOCK_NAME_STATIC);
            lockStatic.setReferenceCounted(true);
        }
        return(lockStatic);
    }

    public WakeReminderIntentService(String name) {
        super(name);
    }

    // When Service is doing Reminder Work , this method is waiting to finish the last step of lock release from Power Manager
    @Override
    final protected void onHandleIntent(Intent intent) {
        try {
            doReminderWork(intent);
        }
        finally {
            getLock(this).release();
        }
    }
}

