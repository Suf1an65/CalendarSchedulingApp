package com.example.calendarviewexample;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Tasks extends AppCompatActivity
{
    public static ArrayList<Tasks> tasksList = new ArrayList<>();

    public static ArrayList<Tasks> tasksForDate(LocalDate date)
    {
        ArrayList<Tasks> tasks = new ArrayList<>(); //creates a list of tasks

        for(Tasks task: tasksList) //adds a task for every real date
        {
            if (task.getDate().equals(date))
                tasks.add(task);
        }


        return tasks;
    }

    private String name;
    private LocalDate date;
    private LocalTime time;

    public int calculatedTime;

    private int totalTime;
    







    public Tasks(String name, LocalDate date, LocalTime time, int calculatedTime)
    {
        this.name = name;
        this.date = date;
        this.time = time;
        this.calculatedTime = calculatedTime;



    }



    //These are all the get and set attributes for attributes of the task class
    public int getCalculatedTime()
    {
        return calculatedTime;
    }

    public void setCalculatedTime(int calculatedTime)
    {
        this.calculatedTime = calculatedTime;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
