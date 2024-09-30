package com.example.calendarviewexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Locale;

public class PlanEvent extends AppCompatActivity { // everything regarding the plan event function

    Button timeButton;
    int hour, minute, calculatedTime, currentCount, countNeeded;

    ArrayList<String> TheUserList;

    ArrayList<ArrayList<Integer>> UsersList;

    ArrayList<Integer> UserSchedule;

    LocalDate DaySearched, DayForUser;

    Boolean DayFound;


    EditText EditEventName, UserEntry;

    Button AddUserButton, PlanEventButton;

    String eventName, enteredUser;

    TextView userList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_event);
        //initialising all the entry fields and buttons of the plan_event_activity
        timeButton = findViewById(R.id.newTimeButton);
        EditEventName = findViewById(R.id.EventNameEdit);
        UserEntry = findViewById(R.id.EnterUserField);
        AddUserButton = findViewById(R.id.AddUser);
        PlanEventButton = findViewById(R.id.PlanEvent);
        TheUserList = new ArrayList<>();
        UsersList = new ArrayList<>();




        userList = findViewById(R.id.UserList);

        //// This is for adding all the users that are being searched for into a list and checking they
        /// exist in the database

        AddUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enteredUser = UserEntry.getText().toString();

                /// This code will enter the entered user to query if it exists in the database
                Login.reference.child(enteredUser).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        
                        if (task.isSuccessful())
                        {
                            if (task.getResult().exists())
                            {
                                /// if the queried user exists in the database then it will add these users to the list of users that have been entered
                                TheUserList.add(enteredUser);
                                // this helps the user to see what users they have entered
                                userList.setText(String.valueOf(TheUserList));

                                UserSchedule = new ArrayList<>(); //since the user selected exists this will get the user's schedule
                                UsersList.add(UserSchedule); //creates a list of users from those tha will be added to the plan event feature
                                DayForUser = LocalDate.now();
                                for (int i =0; i < 200; i++) //looks through first 200 hundred days of all their schedules
                                {
                                    Login.reference.child(enteredUser).child(String.valueOf(DayForUser)).child("Time for day").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            int TimeForDay = snapshot.getValue(int.class);
                                            Log.d("CheckUserSchedule", " The value for Day " + String.valueOf(DayForUser) + "is " + String.valueOf(TimeForDay));
                                            UserSchedule.add(TimeForDay);




                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            Toast.makeText(PlanEvent.this, "Failed to get Data", Toast.LENGTH_SHORT).show();


                                        }
                                    });
                                    DayForUser = DayForUser.plusDays(1);



                                }







                            }
                            else
                            {
                                /// if the user does not exist in the database
                                Toast.makeText(PlanEvent.this, "This suer does not exist", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else 
                        {
                            Toast.makeText(PlanEvent.this, "Data not found", Toast.LENGTH_SHORT).show();
                        }
                        
                    }
                });




            }

        });

        PlanEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TheUserList.isEmpty()) // checks to make sure users have actually been invited.
                {
                    Toast.makeText(PlanEvent.this, "you have to add users",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Log.d("CalculatedTime Checker", String.valueOf(calculatedTime));
                    eventName = EditEventName.getText().toString();
                    findTime(eventName, calculatedTime); // if users have been invited the find time event is finally called


                }

            }
        });







    }

    private void findTime(String eventName, int  EventTime) // This function actually fins time for the users
    {
        DayFound = false;
        countNeeded = UsersList.size(); //count needed set as this is the amount of users that a day needs to be suitable for for the day to
        // be suitable
        int DaysChecked = 0;
        DaySearched = LocalDate.now(); //Starts from the current date


        while (!DayFound && DaysChecked < 200) { //Checks 200 hundred times or until the date being searched is found

            currentCount = 0;
            for (int e = 0; e < UsersList.size(); e++)
            {
                ArrayList<Integer> currentSchedule = UsersList.get(e); //looks through every user in the user list if there available on said day
                if (currentSchedule.get(DaysChecked) + EventTime < 1440 )
                {
                    currentCount = currentCount + 1; // if they are the count is incremented
                }
                else
                {
                    currentCount = 0; // if not its's decremented so the next date can be checked
                }

            }
            if (currentCount == countNeeded)
            {
                DayFound = true;
                Tasks newEvent = new Tasks(eventName, DaySearched.plusDays(DaysChecked), LocalTime.now(), EventTime);
                Day.enoughTimeChecker(DaySearched.plusDays(DaysChecked), EventTime, newEvent, Login.username);
                Toast.makeText(PlanEvent.this, "Available date at " + String.valueOf(DaySearched.plusDays(DaysChecked)), Toast.LENGTH_LONG ).show();
                // This finally checks if a suitable date has been found
                //and adds that date into the user's schedule as well as tells them when the day available occurs
            }
            DaysChecked = DaysChecked +1;

        }
        if (DaysChecked >= 200)
        {
            Toast.makeText(this, "No available spaces in your schedules to do this", Toast.LENGTH_SHORT).show();
            // if no available days an error message is sent.
        }


    }


    public void openTimePicker(View view) //This creates the time picker for the user to set the desired time of the event they're planning.
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                hour = selectedHour;
                minute = selectedMinute;
                timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
                calculatedTime = (hour *60) + minute;



            }
        };


        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }
}