package com.example.calendarviewexample;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    private final ArrayList<LocalDate> days;
    private final OnItemListener onItemListener;

    public CalendarAdapter(ArrayList<LocalDate> days, OnItemListener onItemListener) // sets the output for each box in the monthly calendar view
            // grid
    {
        this.days = days;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) // This function actually creates the box which
            // stores the view for the monthly and weekly calendar views.
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if(days.size() > 15)
            layoutParams.height = (int) (parent.getHeight() * 0.166666666); //sets size of each box
        else
            layoutParams.height = (int) parent.getHeight();
        return new CalendarViewHolder(view, onItemListener, days); //returns the calendar view
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position)
    {
        final LocalDate date = days.get(position);

        if (date == null)
            holder.dayOfMonth.setText(""); //sets the text to empty for days that don't exist.
        else
        {
            holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));
            if (date.equals(CalendarUtils.selectedDate))
                holder.parentView.setBackgroundColor(Color.LTGRAY); // if not it will set the text in the box to the corresponding date in
            // the calendar view

        }
    }

    @Override
    public int getItemCount()
    {
        return days.size();
    } // Simply returns the size of the day as a numerical value

    public interface  OnItemListener
    {
        void onItemClick(int position, LocalDate date);
    }
}

