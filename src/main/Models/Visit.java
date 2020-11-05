package main.Models;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A model that represents an individual visit
 *
 * @author Alanna Morris
 */
public class Visit implements Serializable {

    private long VisitorID;
    private Date ArrivalTime = null;
    private Date DepartureTime = null;

    /**
     * Creates a new visit object with the specified date/time as the arrival time
     * @param VisitorID the ID of the visitor
     */
    public Visit(long VisitorID, Date arrivalTme) {

        this.VisitorID = VisitorID;
        ArrivalTime = arrivalTme;
    }

    /**
     * Updates the departure time to end the visit
     */
    public void endVisit(Date departureTime) {
        DepartureTime = departureTime;
    }

    public boolean getIsOngoingVisit() {return DepartureTime == null; }
    public long getVisitorID() {return VisitorID; }

    public long getLengthOfVisit() {
        if(DepartureTime != null)
            return DepartureTime.getTime() - ArrivalTime.getTime();

            return 0;
    }

    /**
     * returns a string of the length of the visit in an hh:mm:ss format
     * @returna string of the length of the visit in an hh:mm:ss format
     */
    public String getFormatedLengthOfVisit(){
        int seconds = 0;
        int minutes = 0;
        int hours = 0;

        //length is measured in milliseconds, so dividing by 1000 will give us seconds
        long length = this.getLengthOfVisit();
        seconds = (int) (length/1000);
        //account for minutes
        if(seconds >= 60){
            minutes = seconds / 60;
            seconds = seconds % 60;
        }
        //account for hours
        if(minutes >= 60){
            hours = minutes / 60;
            minutes = minutes % 60;
        }

        String s = customFormat(hours) + ":" + customFormat(minutes) + ":" + customFormat(seconds);
        return s;
    }

    /**
     * helper method to format the hours, minutes, and seconds in various methods
     * @param value the string being formatted
     * @return the integer passed in to a guaranteed two digits
     */
    static private String customFormat( int value ) {
        DecimalFormat myFormatter = new DecimalFormat("00");
        String output = myFormatter.format(value);
        return (output);
    }

    public String getEndTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        if(this.DepartureTime != null){
            return sdf.format(this.DepartureTime);
        }

        return null;
    }

    @Override
    public String toString() {
        return "VisitorID=" + VisitorID +
                ", ArrivalTime=" + ArrivalTime;
    }
}
