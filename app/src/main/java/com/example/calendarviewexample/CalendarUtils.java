package com.example.calendarviewexample;

import android.widget.TextView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendarUtils // This file creates and sets many frameworks and function needed for the weekly and monthly views
{
    public static LocalDate selectedDate;


    public static String monthYearFromDate(LocalDate date) // Creates a string which returns the month in a way that resembles the format used
            // for the monthly view
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy"); // This format can be changed however it is in this format
        // for the monthly view
        return date.format(formatter);
    }

    public static ArrayList<LocalDate> daysInMonthArray(LocalDate date) // creates the days in month array needed for the monthly calendar view
    {
        ArrayList<LocalDate> daysInMonthArray = new ArrayList<>(); //Creates an array which stores the dayos of the month
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth(); //gets the number of days for the month selected.

        LocalDate firstOfMonth = CalendarUtils.selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i = 1; i <= 42; i++)
        {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek)
            {
                daysInMonthArray.add(null);
            }
            else
            {
                daysInMonthArray.add(LocalDate.of(selectedDate.getYear(), selectedDate.getMonth(), i- dayOfWeek));
                // adds day to the dayInMonth Array
            }
        }
        return  daysInMonthArray;
    }

    public static ArrayList<LocalDate> daysInWeekArray(LocalDate selectedDate) // creates the days in week array for the weekly calendar view
    {
        ArrayList<LocalDate> days = new ArrayList<>();
        LocalDate current = sundayForDate(selectedDate);
        LocalDate endDate = current.plusWeeks(1);

        while (current.isBefore(endDate))
        {
            days.add(current);
            current = current.plusDays(1);
        }
        return days;

    }

    private static LocalDate sundayForDate(LocalDate current) //finds the first sunday and sets it to the date corresponding to the month as
            // this is because the weekly view begins with Sunday rather than Monday
    {
        LocalDate oneWeekAgo = current.minusWeeks(1);

        while(current.isAfter(oneWeekAgo))
        {
            if(current.getDayOfWeek() == DayOfWeek.SUNDAY)
                return current;

            current = current.minusDays(1);
        }
        return null;
    }


    public static String formattedDate(LocalDate date) //formats the date in a manner which resembles Western human understanding of date.
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        return date.format(formatter);
    }

    public static String formattedTime(LocalTime time) //function which creates a time formatting however this is not outputted in the application
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        return time.format(formatter);
    }
}
