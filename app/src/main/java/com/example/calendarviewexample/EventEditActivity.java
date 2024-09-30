package com.example.calendarviewexample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.time.LocalTime;
import java.util.Locale;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EventEditActivity extends AppCompatActivity
{


    private EditText eventNameET;
    private TextView eventDateTV, eventTimeTV;
    private LocalTime time;

    public CheckBox DailyButton, WeeklyButton;

    Button timeButton;
    int hour, minute;

    private int calculatedTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();


        time = LocalTime.now();
        //initialises all the buttons and text views
        eventDateTV.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        eventTimeTV.setText("time: " + CalendarUtils.formattedTime(time));
        timeButton = findViewById(R.id.timeButton);
        DailyButton = findViewById(R.id.RecurDailyButton);
        WeeklyButton = findViewById(R.id.RecurWeeklyButton);



    }






    private void initWidgets()
    {
        eventNameET = findViewById(R.id.EventNameEdit);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeTV = findViewById(R.id.eventTimeTV);
    }

    public void SaveAction(View view) // This function actually creates the task for when they press the save button
    {
        String taskName = eventNameET.getText().toString(); //gets the task name from when they entered it in

        /// this is where the attributes of the task are set

        if (WeeklyButton.isChecked()) //this checks if the user want to recur their task weekly
        {
            for (int i =0; i <52; i++) // if so it will be added into their calendar every week for the next year
            {
                Tasks newTasks = new Tasks(taskName, CalendarUtils.selectedDate, time, calculatedTime); //creates a new object for every task in the
                // calendar
                Day.enoughTimeChecker(CalendarUtils.selectedDate, calculatedTime, newTasks, Login.username);
                //This checks to make sure in every day the tasks is added there is enough time for said task
                Log.d("Weekly function", String.valueOf(newTasks.getDate()) + "  " + String.valueOf(newTasks.calculatedTime));
                CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
            }
        }
        else if (DailyButton.isChecked()) //checks to see if the user wants their task to recur daily
        {
            for (int i =0; i < 225; i++) //if they do this task will be inserted into every day of their calendar for the nect 225 days
            {
                Tasks newTasks = new Tasks(taskName, CalendarUtils.selectedDate, time, calculatedTime);
                Day.enoughTimeChecker(CalendarUtils.selectedDate, calculatedTime, newTasks, Login.username);
                //This checks to make sure in every day the tasks is added there is enough time for said task
                Log.d("Weekly function", String.valueOf(newTasks.getDate()) + "  " + String.valueOf(newTasks.calculatedTime));
                CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusDays(1);
            }
        }

        else //This is for if the user just wants to add one task
        {
            Tasks newTasks = new Tasks(taskName, CalendarUtils.selectedDate, time, calculatedTime);
            Day.enoughTimeChecker(CalendarUtils.selectedDate, calculatedTime, newTasks, Login.username);
        }


        Log.d("Task List", "Number of items in task list is" + String.valueOf(Tasks.tasksList.size()));

        /// This function will make it such that the total time cannot exceed the amount of hours in a day
        finish();


    }





    public void openTimePicker(View view) // this creates the timepicker for the user to enter the duration of the task
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                hour = selectedHour;
                minute = selectedMinute;
                timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
                calculatedTime = (hour *60) + minute;
                // calculated time is the total time in minutes used to calculate free time
            }
        };


        int style = AlertDialog.THEME_HOLO_DARK; //sets the format of the timepicker

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }


}