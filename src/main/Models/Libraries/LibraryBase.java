package main.Models.Libraries;

import main.Models.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * The base state of any given library. Acts as the State for the state pattern of the library
 *
 * @author Anthony Ferrioli
 * @author Joseph Saltalamacchia
 */
public abstract class LibraryBase implements Serializable {

    public enum LibraryStatus {Default, Open, Closed};
    public LibraryStatus libraryStatus;

    //Not sure if should be here or in openlibrary
    protected HashMap<Long, LibraryEntry> Inventory = new HashMap<Long, LibraryEntry>();
    protected HashMap<Long, Visitor> Register = new HashMap<Long, Visitor>();
    protected ArrayList<Visit> Visits = new ArrayList<Visit>();

    protected TimeManager time;

    public LibraryBase() {
        time = TimeManager.getInstance();
    }

    //abstract methods
    public abstract boolean addVisitor(Visitor visitor);
    public abstract Visit startVisit(Long visitorID);
    public abstract Visit endVisit(Long visitorID);
    public abstract boolean visitorCheckOut(Visitor visitor, Long ISBN);
    public abstract boolean borrowBook(Long VisitorID, Long ISBN);

    public String getTime(){
        return time.toString();
    }

    public void advanceTime(int days, int hours){
        time.addDays(days);
        time.addHours(hours);
    }

    public HashMap<Long, Visitor> getRegister() {
        return Register;
    }

    public HashMap<Long, LibraryEntry> getInventory() {
        return Inventory;
    }

    public ArrayList<Visit> getVisits(){
        return Visits;
    }

    /**
     * purchases books from the bookstore and adds them to the library
     * books can be purchased regardless of if the library is opened or not
     * @param book the book being purchased
     * @param copies the number of copies of that book being purchased
     * @return the entry in the library for the books purchased
     */
    public LibraryEntry addBook(Book book, int copies){

        LibraryEntry entry = new LibraryEntry(book, copies);
        long addedISBN = entry.getISBN();

        //if the library entry already exists in the library, just increase the number of copies it has
        if(Inventory.containsKey(addedISBN)) {
            Inventory.get(addedISBN).buyMoreCopies(copies);
        }
        else {
            Inventory.put(addedISBN, entry);
        }

        return Inventory.get(addedISBN);
    }
    @Override
    public String toString() {
        return "OwningLibrary{" +
                "Inventory=" + Inventory.toString() +
                ", Register=" + Register.toString() +
                '}';
    }

    /**
     * /searches the library to see if it owns at least one copy of a given book
     * @param ISBN the ISBN of the book in question
     * @return True if the book exists, false otherwise
     *
     */
    public boolean containsBook(long ISBN){
        return this.Inventory.containsKey(ISBN);
    }

    public ArrayList<String> statistics() {
        ArrayList<String> report = new ArrayList<>();
        int totalBooks = 0;
        int totalVisitors = 0;
        double totalFines = 0;

        for (long key : Inventory.keySet()) {
            totalBooks += Inventory.get(key).getTotalCopies();
        }
        report.add(Integer.toString(totalBooks));
        for (long key : Register.keySet()) {
            totalVisitors += 1;
            totalFines += Register.get(key).getTotalFines();
        }
        report.add(Integer.toString(totalVisitors));
        report.add(Double.toString(totalFines));

        return report;
    }

    //***************** Checks if open status by verifying if it is a Openlibrary class
    public boolean checkIfOpen() {
        if (this instanceof OpenLibrary) {
            return true;
        } else {
            return false;
        }
    }

    //***************** Sets library status to open and returns a new OpenLibrary instance
    public OpenLibrary forceOpen() {
        this.libraryStatus = LibraryStatus.Open;
        OpenLibrary library = new OpenLibrary(this);
        return library;
    }

}
