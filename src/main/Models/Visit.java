package main.Models;

import java.util.ArrayList;
import java.util.Date;

/**
 * A model that represents an individual visit
 *
 * @author Alanna Morris
 */
public class Visit {

    private int VisitorID;
    private Date ArrivalTime = null;
    private Date DepartureTime = null;

    /**
     * Creates a new visit object with the specified date/time as the arrival time
     * @param VisitorID the ID of the visitor
     */
    public Visit(int VisitorID, Date arrivalTme) {

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
    public int getVisitorID() {return VisitorID; }

    public long getLengthOfVisit() {
        if(DepartureTime != null)
            return DepartureTime.getTime() - ArrivalTime.getTime();

            return 0;
    }
}
