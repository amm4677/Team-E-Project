package main.Models;


import java.io.Serializable;

import main.Models.StateCheckOut.ableToCheckOut;
import main.Models.StateCheckOut.checkOutState;
import main.Models.StateCheckOut.unableToCheckOut;
import main.Models.StrategyCostCalc.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * A model the represents a visitor to a library
 *
 * @author Joseph Saltalamacchia
 */
public class Visitor implements Serializable {

    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private long ID;
    private ArrayList<CheckedOut> booksCheckedOut;  //this structure means that the first copy of a
    private double totalFines;   //TODO this can probably be removed and replaced with a class that calculates the value

    /**
     * A constructor to create a new Visitor, using the first name, last name, address, phone number, and ID
     *
     * @param firstName  The first name of the visitor
     * @param lastName  The last name of the visitor
     * @param address  The address of the visitor
     * @param phoneNumber  The phone number of the visitor
     * @param ID  The unique ID number of the visitor
     */
    public Visitor(String firstName, String lastName, String address, String phoneNumber, long ID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.ID = ID;
    }

    /**
     * returns the first name of the Visitor
     * @return the first name of the visitor
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the visitor to the entered string
     * @param firstName the new first name of the visitor
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * returns the last name of the Visitor
     * @return the last name of the visitor
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the Last name of the visitor to the entered string
     * @param lastName the new Last name of the visitor
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get the address of the visitor
     * @return the address of the visitor
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set the address of the visitor to the entered string
     * @param address the new address of the visitor
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the phone number of the visitor
     * @return the phone number of the visitor
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the visitor to the entered integer
     * @param phoneNumber the new phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * returns the unique ID number of the visitor
     * @return the unique ID number of the visitor
     */
    public long getID() {
        return ID;
    }

    //there is no "set" method for the ID because it should never change.

    /**
     * returns the total fines owed by this visitor
     * @return a double value representing the total fines owed
     */
    public double getTotalFines(){
        return totalFines;
    }

    /**
     * adds a book to the list of books the visitor currently has checked out
     * @param book the book being checked out
     * @return if the book was successfully added to the list of books checked out.
     */
    public Boolean addCheckedOutBook(Book book)
    {
        checkOutState stateCheckOut = new checkOutState();
        if(this.booksCheckedOut.size() < 5) {
            this.booksCheckedOut.add(new CheckedOut(book, ID, new Date()));
            ableToCheckOut ableTo = new ableToCheckOut();
            return ableTo.canCheckOut(stateCheckOut);
        } else {
            unableToCheckOut unableTo = new unableToCheckOut();
            return unableTo.canCheckOut(stateCheckOut);
        }
    }

    /**
     * removes a book from the list of books the visitor currently has checked out
     * @param checkedOut: the book being checked in
     * @return if the book was successfully removed from the list of books checked out.
     */
    public int checkInBook(CheckedOut checkedOut) {
        Date today = new Date(0);
        int cost;
        long difference;
        long differenceInDays;

        difference = Math.abs(today.getTime() - checkedOut.getDueDate().getTime());
        differenceInDays = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);

        if (differenceInDays <= 7) {
            Transaction transaction = new Transaction(new ReturnedWithin7Days());
            cost = transaction.calculate(checkedOut);
        } else if (differenceInDays <= 14) {
            Transaction transaction = new Transaction(new Returned1WeekLate());
            cost = transaction.calculate(checkedOut);
        } else if (differenceInDays <= 77) {
            Transaction transaction = new Transaction(new Returned2To11WeeksLate());
            cost = transaction.calculate(checkedOut);
        } else {
            Transaction transaction = new Transaction(new Returned12OrMoreWeeksLate());
            cost = transaction.calculate(checkedOut);
        }

        this.totalFines += cost;
        this.booksCheckedOut.remove(checkedOut);
        return cost;

    }


    /**
     * A method used to compare visitors to each other by first and last name, address, and phone nubmber
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o){
        if(!(o instanceof Visitor)){
            return false;
        }
        if(((Visitor) o).firstName.equals(this.firstName) &&
            ((Visitor) o).lastName.equals(this.lastName) &&
            ((Visitor) o).address.equals(this.address) &&
            ((Visitor) o).phoneNumber.equals(this.phoneNumber)){
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "Visitor{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", ID=" + ID +
                ", booksCheckedOut=" + booksCheckedOut +
                '}';
    }

}
