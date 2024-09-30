package com.example.calendarviewexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class EventAdapter extends ArrayAdapter<Tasks>
{

    public EventAdapter(@NonNull Context context, List<Tasks> tasks)
    {
        super(context, 0, tasks);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) /// creates how the task is outputted to the user
            // all of this is put into a listview so the task flow freely and are visually aesthetically pleasing
    {
        Tasks tasks = getItem(position);
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_cell, parent, false);
        /// gets the cell of the corresponding date of where you want to put the task
        TextView eventCellTV = convertView.findViewById(R.id.eventCellTV);

        String taskTitle = tasks.getName() +  "  " + tasks.getCalculatedTime() + " minutes";
        // use this to set what the actual message will be in the listview underneath a day
        eventCellTV.setText(taskTitle);
        return convertView;
    }
}
