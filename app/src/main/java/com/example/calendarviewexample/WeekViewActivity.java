package com.example.calendarviewexample;

import static com.example.calendarviewexample.CalendarUtils.daysInWeekArray;
import static com.example.calendarviewexample.CalendarUtils.monthYearFromDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;

public class WeekViewActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {

    private TextView monthYearText; //initialises textview
    private RecyclerView calendarRecyclerView; //initialises calendar recyclerview
    private ListView taskListView; //initialises the task list where the tasks are outputted

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        initWidgets();
        setWeekView(); // this functions sets the weekly view for the suers
        
    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        taskListView = findViewById(R.id.taskListView);
    }

    private void setWeekView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate)); // gets the same dates as those form the monthly view as the
        // numbers representing days in the weekly view still correspond to the month.
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        // sets the grid layout for the weekly view
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);

        setTaskAdapter();
    }



    public void previousWeekAction(View view) //sends user to previous week
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();

    }

    public void nextWeekAction(View view) // sends user to next week
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date) // highlights date that is selected in the weekly view
    {
        CalendarUtils.selectedDate = date;
        setWeekView();

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setTaskAdapter();
    }


    private void setTaskAdapter()
    {
        ArrayList<Tasks> dailyTasks = Tasks.tasksForDate(CalendarUtils.selectedDate);
        //setting the variable of the task to the date selected in the calendar view
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyTasks );

        taskListView.setAdapter(eventAdapter);
        // adds the tasks to the listview underneath the weekly calendar view

    }

    public void NewEventAction(View view) //sends the user to the create task form
    {
        startActivity(new Intent(this, EventEditActivity.class));
    }
}