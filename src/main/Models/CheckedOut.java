package main.Models;

import java.util.Date;
import java.util.Calendar;

/**
 * A class that holds a checked out book ISBN, the visitor ID, the checkout date,
 * and the due date
 *
 * @author Alanna Morris
 */

public class CheckedOut {

    private Book book;
    private long VisitorID;
    private Date CheckoutDate;
    private Date DueDate;

    public CheckedOut(Book book, long VisitorID, Date CheckoutDate) {
        this.book = book;
        this.VisitorID = VisitorID;
        this.CheckoutDate = CheckoutDate;

        Calendar newDate = Calendar.getInstance();
        newDate.setTime(CheckoutDate);
        newDate.add(Calendar.DATE, 7);
        DueDate = newDate.getTime();
    }

    public Book getBook() {
        return book;
    }

    public Date getDueDate(){
        return DueDate;
    }
}
