package com.mayank.user.mytaskreminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by user on 08-07-2016.
 */
public class CustomAdapter extends ArrayAdapter<String> {

    private String[] dateTimes;
    public CustomAdapter(Context context, String[] myTitles,String[] dateTimes) {
        super(context, R.layout.reminder_row, myTitles);
        this.dateTimes = dateTimes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        View customView = myInflater.inflate(R.layout.reminder_row, parent, false);

        String currentTitle = getItem(position);

        TextView myTitle = (TextView) customView.findViewById(R.id.myTitle);
        TextView myDateTime = (TextView) customView.findViewById(R.id.myDateTime);

        myTitle.setText(currentTitle);
        myDateTime.setText(dateTimes[position]);
        return customView;
    }
}