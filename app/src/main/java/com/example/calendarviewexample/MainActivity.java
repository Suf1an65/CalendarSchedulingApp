package com.example.calendarviewexample;

import static com.example.calendarviewexample.CalendarUtils.daysInMonthArray;
import static com.example.calendarviewexample.CalendarUtils.monthYearFromDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener

{   //This screen is the main page however also contains the monthly calendar view and also the buttons which will take the users to other screens
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // The second the screen is loaded these functions are called and executed
        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now(); /// The selected date at the beginning is set to the current date (today)
        setMonthView(); // This function will create the monthly view

    }

    private void initWidgets() //This function simply sets the widgets of the monthly view
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    private void setMonthView()
    {

        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate)); /// This will set the month and year at the top of the monthly view to the
        // accurate month relating to the year
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        /// sets the layout to seven spaces for the seven days in the week
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter); /// This code will set the correct day relating to the month for each day in the grid
        // as the calendar adapter has set the day to the daysInMonthArray.
    }



    public void previousMonthAction(View view) // This function will take the user a month back on the calendar monthly view
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view) // This will put the user a month forward on the calendar monthly view
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        if(date != null) // This will check to make sure if a certain date doesn't exist e.g. 31 February this cannot be a date
            // in the calendar view
        {
            CalendarUtils.selectedDate = date;
            setMonthView();
        }

    }

    public void WeeklyAction(View view)
    {
        startActivity(new Intent(this, WeekViewActivity.class));
    }
    // This function takes the user to the weekly view




    public void GoToLogin(View view)
    {
        startActivity(new Intent(this, Login.class));
    }
    // This function takes the user to the login screen

    public void GoToPlanEventPage(View view)
    {
        startActivity(new Intent(this, PlanEvent.class));
    }
    // This function takes the user to the Plan event screen
}