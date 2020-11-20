package main.Models;

import Requests.Validator;

import java.io.Serializable;
import java.util.Date;
import java.util.Calendar;

/**
 * A class that holds a checked out book ISBN, the visitor ID, the checkout date,
 * and the due date
 *
 * @author Alanna Morris
 */

public class CheckedOut implements Serializable {

    private Book book;
    private long VisitorID;
    private String CheckoutDate;
    private String DueDate;

    public CheckedOut(Book book, long VisitorID, String CheckoutDate) {
        this.book = book;
        this.VisitorID = VisitorID;
        this.CheckoutDate = CheckoutDate;

        TimeManager time = TimeManager.getInstance();

        Date today = time.getDate();
        //a placeholder claendar for formatting reasons
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        //the books is always due seven days from checkout
        c.add(Calendar.DAY_OF_YEAR,7);
        Date dueDate = c.getTime();
        String dueDateString = time.getFormattedDate(dueDate);

        DueDate = dueDateString;
    }

    public Book getBook() {
        return book;
    }

    public long getVisitorID(){
        return VisitorID;
    }

    public String getDueDate(){
        return DueDate;
    }
}
