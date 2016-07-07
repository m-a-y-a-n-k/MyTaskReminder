package com.mayank.user.mytaskreminder;

/**
 * Created by user on 07-07-2016.
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.database.Cursor;
import android.util.Log;

public class OnBootReceiver extends BroadcastReceiver {

    private static final String TAG = ComponentInfo.class.getCanonicalName();

    // On Boot fetch all Reminders by invoking from database and set them using Reminder Manager

    @Override
    public void onReceive(Context context, Intent intent) {

        ReminderManager reminderMgr = new ReminderManager(context);

        RemindersDbAdapter dbHelper = new RemindersDbAdapter(context);
        dbHelper.open();

        Cursor cursor = dbHelper.fetchAllReminders();

        if(cursor != null) {
            cursor.moveToFirst();

            int rowIdColumnIndex = cursor.getColumnIndex(RemindersDbAdapter.KEY_ROWID);
            int dateTimeColumnIndex = cursor.getColumnIndex(RemindersDbAdapter.KEY_DATE_TIME);

            // loop that fetches from database using cursor and passes date and time in a calendar object to set reminder

            while(cursor.isAfterLast() == false) {

                Log.d(TAG, "Adding alarm from boot.");
                Log.d(TAG, "Row Id Column Index - " + rowIdColumnIndex);
                Log.d(TAG, "Date Time Column Index - " + dateTimeColumnIndex);

                Long rowId = cursor.getLong(rowIdColumnIndex);
                String dateTime = cursor.getString(dateTimeColumnIndex);            // date and time stored as Strings in DB

                Calendar cal = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat(ReminderEditActivity.DATE_TIME_FORMAT);

                try {
                    java.util.Date date = format.parse(dateTime);           // Create a date object by formatting 'dateTime' String
                    cal.setTime(date);                                      // Set Time( and Date) in Calendar

                    reminderMgr.setReminder(rowId, cal);
                } catch (java.text.ParseException e) {
                    Log.e("OnBootReceiver", e.getMessage(), e);
                }

                cursor.moveToNext();
            }
            cursor.close() ;
        }

        dbHelper.close();
    }
}
