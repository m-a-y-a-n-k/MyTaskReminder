package com.mayank.user.mytaskreminder;

import android.annotation.TargetApi;
import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.AdapterContextMenuInfo;

/**
 * Created by user on 08-07-2016.
 */
public class ReminderListFragment extends ListFragment {

    private static final int ACTIVITY_CREATE=0;
    private static final int ACTIVITY_EDIT=1;
    private RemindersDbAdapter mDbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reminder_list, container, false);
        mDbHelper = new RemindersDbAdapter(getActivity());
        mDbHelper.open();

        fillData();
        return view;
    }

    public ReminderListFragment() {
        super();
    }

    private void fillData() {
        Cursor remindersCursor = mDbHelper.fetchAllReminders();
        getActivity().startManagingCursor(remindersCursor);

        // Create an array to specify the fields we want to display in the list (only TITLE)
        String[] from = new String[]{RemindersDbAdapter.KEY_TITLE};

        // and an array of the fields we want to bind those fields to (in this case just text1)
        int[] to = new int[]{R.id.myTitle};

        // Now create a simple cursor adapter and set it to display
        SimpleCursorAdapter reminders =
                new SimpleCursorAdapter(getActivity(), R.layout.reminder_row, remindersCursor, from, to);
        setListAdapter(reminders);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        registerForContextMenu(getListView());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater mi = getActivity().getMenuInflater();
        mi.inflate(R.menu.list_menu_item_longpress, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_delete:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                mDbHelper.deleteReminder(info.id);
                fillData();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(getActivity(), ReminderEditActivity.class);
        i.putExtra(RemindersDbAdapter.KEY_ROWID, id);
        startActivityForResult(i, ACTIVITY_EDIT);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuInflater mi = getActivity().getMenuInflater();
        mi.inflate(R.menu.list_menu, menu);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.menu_insert:
                createReminder();
                return true;
            case R.id.menu_settings:
                Intent i = new Intent(getActivity(), TaskPreferences.class);
                startActivity(i);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createReminder() {
        Intent i = new Intent(getActivity(), ReminderEditActivity.class);
        startActivityForResult(i, ACTIVITY_CREATE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        fillData();
    }
}
