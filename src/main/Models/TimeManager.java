package main.Models;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Anthony Ferraioli
 */
public class TimeManager implements Serializable {

    private Calendar calendar;
    private SimpleDateFormat format;
    private long lastUpdatedTime;

    /*
        The default constructor for the class
     */
    public TimeManager()
    {
        calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        format = new SimpleDateFormat("yyyy-MM-dd");
        lastUpdatedTime = System.currentTimeMillis();
    }

    public TimeManager(String date, String time)
    {
        format = new SimpleDateFormat("yyyy-MM-dd");
        calendar = Calendar.getInstance();

        //set the date for the calendar
        try {
            calendar.setTime(format.parse(date));
        } catch (ParseException e) {
            //Do something
        }

        //set the time for the calendar
        //can only set as specific as the nearest second
        String[] t = time.split(":");
        calendar.set(Calendar.HOUR, Integer.parseInt(t[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(t[1]));
        calendar.set(Calendar.SECOND, Integer.parseInt(t[2]));

        //update the last time updated
        lastUpdatedTime = System.currentTimeMillis();
    }

    /*
        Adds a specified number of days to the calendar
     */
    public void addDays(int days)
    {
        calendar.add(Calendar.DAY_OF_MONTH, days);
    }

    /*
        Adds a specified number of hours to the calendar
     */
    public void addHours(int hours)
    {
        calendar.add(Calendar.HOUR, hours);
    }

    /*
        Updates the calendar to be accurate with current time difference and returns a Date object
     */
    public Date getDate()
    {
        UpdateCalendar();
        return calendar.getTime();
    }

    public DateFormat getFormat()
    {
        return format;
    }

    /*
        Finds the difference in milliseconds from the last time the Calendar was checked and updates it based on that information
     */
    private void UpdateCalendar()
    {
        long newUpdatedTime = System.currentTimeMillis();

        //find the number of milliseconds passed since last date check
        int timeDifference = (int)(newUpdatedTime - lastUpdatedTime);

        //add the difference in milliseconds to calendar
        calendar.add(Calendar.MILLISECOND, timeDifference);

        //update the last updated time
        lastUpdatedTime = newUpdatedTime;
    }

    /*
        Returns a string of the current date
     */
    @Override
    public String toString()
    {
        this.UpdateCalendar();

        return "TimeManager{" +
                "Date=" + format.format(calendar.getTime()) +
                "Time=" + calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND) +
                "}";
    }
}