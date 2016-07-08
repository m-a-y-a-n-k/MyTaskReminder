package com.mayank.user.mytaskreminder;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by user on 08-07-2016.
 */
public class ReminderAdapter extends CursorAdapter {
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public ReminderAdapter(Context context, Cursor c) {
        super(context, c);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v = mLayoutInflater.inflate(R.layout.reminder_row, parent, false);
        return v;
    }

    @Override
    public void bindView(View v, Context context, Cursor c) {
        String title = c.getString(c.getColumnIndexOrThrow(RemindersDbAdapter.KEY_TITLE));
        String dateTime = c.getString(c.getColumnIndexOrThrow(RemindersDbAdapter.KEY_DATE_TIME));

        /**
         * Next set the title of the entry.
         */

        TextView title_text = (TextView) v.findViewById(R.id.myTitle);
        if (title_text != null) {
            title_text.setText(title);
        }

        /**
         * Set Date
         */

        TextView date_text = (TextView) v.findViewById(R.id.myDateTime);
        if (date_text != null) {
            date_text.setText(dateTime);
        }

        /**
         * Decide if we should display the paper clip icon denoting image attachment
         */

        ImageView myCover = (ImageView) v.findViewById(R.id.myCover);
        myCover.setImageResource(R.drawable.icon);
    }
}
