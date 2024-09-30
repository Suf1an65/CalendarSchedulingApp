package com.example.calendarviewexample;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.util.ArrayList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Day extends AppCompatActivity
{



    private LocalDate date;
    private int totalTime;




    public Day(LocalDate date, int totalTime) //creates a class days. This is needed to set the time for each day that is being used up for both the
            //database and to see how much free time there is when adding a task
    {
        this.date = date;
        this.totalTime = totalTime;
    }


    static ArrayList<Day> listOfDayObjects = new ArrayList<>();




    public static void enoughTimeChecker(LocalDate date, int addedTime, Tasks theTasks, String userName)

            // checks if there's enough time available in the day then adds it into the schedule if there's enough free time
    {
        Log.d("SizeChecker", "the size of ListOfDayObjects is " + String.valueOf(listOfDayObjects.size()));

        for(int i =0; i<listOfDayObjects.size(); i++)
        {
            if (listOfDayObjects.get(i).getDate().equals(date))
            {
                int x = listOfDayObjects.get(i).getTotalTime();
                if(x + addedTime < 1441) //set to 1441 as that is the amount of minutes available in a day.
                {
                    int newTime = x + addedTime;
                    listOfDayObjects.get(i).setTotalTime(newTime);
                    Login.reference.child(userName).child(String.valueOf(listOfDayObjects.get(i).getDate())).child("Time for day").setValue(newTime);
                    // adds time for task to the database
                    Log.d("EnoughTimeChecker", "total time of " + String.valueOf(listOfDayObjects.get(i).getDate()) + "" + String.valueOf(listOfDayObjects.get(i).getTotalTime()));
                    Tasks.tasksList.add(theTasks);
                    //adds task to the task list

                }
                else
                {
                    Log.d("EnoughTimeChecker", "total time exceeds 24 hours.");
                }


            }

        }

    }

    public static void setDayClass() //creates day object for every date from today to the next two years so that the suer can dor the enough time checker for every day
            // they can conceive
    {
        LocalDate currentDate = LocalDate.now();
        for (int i =0;  i < 730; i++)
        {
            Day newDay = new Day(currentDate, 0); //sets the total time to 0 at first
            listOfDayObjects.add(newDay);
            currentDate = currentDate.plusDays(1);
            Log.d("DayClazz", "a new object has been created, the size of listOfDayObjects is now" + String.valueOf(listOfDayObjects.size()) + String.valueOf(currentDate) + String.valueOf(newDay.getTotalTime()));
        }

    }

    //get and se
    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    public int getTotalTime()
    {
        return totalTime;
    }

    public void setTotalTime(int totalTime)
    {
        this.totalTime = totalTime;
    }
}
