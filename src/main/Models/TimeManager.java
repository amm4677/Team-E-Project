package main.Models;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author Anthony Ferraioli
 */
public class TimeManager implements Serializable {

    private static TimeManager _instance;

    private Date startDate;

    private Calendar calendar;
    private SimpleDateFormat format;
    private long lastUpdatedTime;

    public static TimeManager getInstance() {
        if(_instance == null) _instance = new TimeManager();

        return _instance;
    }

    public static TimeManager createInstance(String date, String time)
    {
        if(_instance != null)
        {
            System.err.println("An instance of TimeManager already exists");
            return _instance;
        }

        _instance = new TimeManager(date, time);
        return _instance;
    }

    /*
        The default constructor for the class
     */
    private TimeManager()
    {
        calendar = Calendar.getInstance();

        startDate = new Date();
        calendar.setTime(startDate);
        format = new SimpleDateFormat("yyyy-MM-dd");
        lastUpdatedTime = System.currentTimeMillis();
    }

    private TimeManager(String date, String time)
    {
        format = new SimpleDateFormat("yyyy-MM-dd");
        calendar = Calendar.getInstance();

        //set the date for the calendar
        try {
            calendar.setTime(format.parse(date));
            startDate = calendar.getTime();
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
    public Date getDate() {
        UpdateCalendar();
        return calendar.getTime();
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
        return "Date=" + getFormattedDate() +
                ", Time=" + getFormattedTime();
    }

    public String getFormattedDate()
    {
        UpdateCalendar();
        return format.format(calendar.getTime());
    }

    public String getFormattedTime()
    {
        UpdateCalendar();
        return calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
    }

    public LocalTime getLocalTime() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S", Locale.US);

        String text = "2011-02-18 05:00:00.0";
        LocalDateTime localDateTime = LocalDateTime.parse(text, formatter);
        return localDateTime.toLocalTime();
    }

    public int daysPassedSinceStartup()
    {
        UpdateCalendar();
        long difference = calendar.getTime().getTime() - startDate.getTime();
        return (int)(difference / (1000*60*60*24));
    }
}