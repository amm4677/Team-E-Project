package main.Models;

import java.io.*;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A model that represents a library using the LMS.
 * The library will keep track of all visitors in a hashmap using their ID
 * and all books using their ISBN
 * @author Alanna Morris
 * @author Joseph Saltalamacchia
 */

public class OwningLibrary implements Serializable{


   //TODO                       /*          DEPRECATED      */
public class OwningLibrary {

    protected HashMap<Long, LibraryEntry> Inventory = new HashMap<Long, LibraryEntry>();
    protected HashMap<Long, Visitor> Register = new HashMap<Long, Visitor>();
    protected ArrayList<Visit> Visits = new ArrayList<Visit>();

    protected TimeManager time;

    /**
     * Creates a library with no books or visitors
     */

    public OwningLibrary(LocalTime openingTime, LocalTime closingTime) {
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        //openLibrary();
    }

    public LibraryEntry addBook(Book book, int copies) {

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

    public void addVisitor(Visitor visitor) {
        Long ID = visitor.getID();
        Register.put(ID, visitor);
    }

    public HashMap<Long, Visitor> getRegister() {
        return Register;
    }

    public HashMap<Long, LibraryEntry> getInventory() {
        return Inventory;
    }

    @Override
    public String toString() {
        return "OwningLibrary{" +
                "Inventory=" + Inventory.toString() +
                ", Register=" + Register.toString() +
                '}';
    }

    /**
     * Starts the visit of a registered visitor
     *
     * @param visitorID the visitor checking out the book, assuming they are not checked in
     * @return the Visit object representing the status of their visit
     */
    public Visit startVisit(long visitorID) {
        //Checks to make sure they aren't already checked in (visitors can check in multiple times per day)
        for (Visit v : Visits) {
            if (v.getVisitorID() == visitorID && v.getIsOngoingVisit()) return v;
        }

        Visit v = new Visit(visitorID, time.getDate());
        Visits.add(v);

        return v;
    }

    /**
     * Ends the present visit of the visitor, if they are currently checked in
     *
     * @param visitorID the visitor visiting the library
     */
    public Visit endVisit(long visitorID) {
        for (Visit v : Visits) {
            if (v.getVisitorID() == visitorID && v.getIsOngoingVisit()) {
                v.endVisit(time.getDate());
                return v;
            }
        }

        return null;
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

    /**
     * Allows a visitor to borrow a book from the library
     * @param VisitorID the ID of the visitor attempting to check out the book
     * @param ISBN the ISBN of the book attempting to be checked out
     * @return true if the book was sussessfully checked out, false otherwise
     */
    public boolean borrowBook(long VisitorID, long ISBN){
        Inventory.get(ISBN).checkoutBook();
        return Register.get(VisitorID).addCheckedOutBook(Inventory.get(ISBN).getBook());
    }

    /**
     * loads the visitors and Books from an external file
     */
    /*public void openLibrary() {
        readVisitors();
        readBooks();
        readTime();
    }*/

    /**
     * Allows a given visitor to check out a book
     *
     * @param visitor the visitor checking out the book
     * @param ISBN    the ISBN of the book being checked out
     * @return if the visitor successfully checked out the book or not
     */
    public Boolean visitorCheckOut(Visitor visitor, long ISBN) {
        if (Inventory.containsKey(ISBN)) {
            //retrive the book objject that the Library entry is wrapping
            Book book = Inventory.get(ISBN).getBook(); //todo: is this line necessary?
            if (Inventory.get(ISBN).canBeCheckedOut() && visitor.addCheckedOutBook(book)) {
                Inventory.get(ISBN).checkoutBook();
                return true;
            }
        }
        return false;
    }

    /**
     * a method that returns a list of visits currently saved by the library, for testing purposes
     * @return the list of visits
     */
    public ArrayList<Visit> getVisits(){
        return Visits;
    }

/*
    private void readTime() {
        try {
            FileInputStream fTime = new FileInputStream(new File("TextFiles/TimeLog.bin"));
            ObjectInputStream oTime = new ObjectInputStream(fTime);

            try {
                //Use file to create TimeManager
                time = (TimeManager) oTime.readObject();
            } catch (EOFException ignored) {
            }
        } catch (FileNotFoundException f) {
            //if no file, create a new TimeManager
            time = new TimeManager();
        } catch (IOException i) {
            System.out.println("Error initializing stream");
        } catch (ClassNotFoundException c) {
            System.out.println("could not find class");
        }
    }
*//*
    private void writeTime() {
        try {
            //create a writer for the visitors
            FileOutputStream fTime = new FileOutputStream(new File("TextFiles/TimeLog.bin"));
            ObjectOutputStream oTime = new ObjectOutputStream(fTime);

            //writes the time object into the file
            oTime.writeObject(time);

        } catch (FileNotFoundException f) {
            System.out.println("Time File Not Found");
        } catch (IOException i) {
            System.out.println("Error initializing stream");
        }
    }*/
/*
    private void writeVisits() {
        try {
            //create a writer for the daily visits
            FileOutputStream fTime = new FileOutputStream(new File("TextFiles/VisitLog-"
                    + time.getFormat().format(time.getDate()) + ".bin"));
            ObjectOutputStream oTime = new ObjectOutputStream(fTime);

            //writes the time object into the file
            oTime.writeObject(Visits);

        } catch (FileNotFoundException f) {
            System.out.println("Visit log file Not Found");
        } catch (IOException i) {
            System.out.println("Error initializing stream");
        }
    }*/
  
/*
    private void writeReport(int month, int year)
    {
        //TODO: Query the log files by month and year, and create the stats from them

        File directory = new File("TextFiles");

        File[] files = directory.listFiles();

        long lengthOfTime = 0;
        int numberOfVisits = 0;

        for(File f : files) {
            if(f.getName().startsWith(String.format("VisitLog-%04d-%02d", year, month))) {
                try {
                    FileInputStream fVisits = new FileInputStream(f);
                    ObjectInputStream oVisits = new ObjectInputStream(fVisits);

                    Object readObject = oVisits.readObject();
                    if(readObject instanceof ArrayList<?>) {
                        ArrayList<Visit> dailyVisits = (ArrayList<Visit>) oVisits.readObject();
                        for(Visit v : dailyVisits) {
                            lengthOfTime += v.getLengthOfVisit();
                            numberOfVisits++;
                        }

                    }

                } catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Report:");
        System.out.println("Number of books owned by the library: ");
        System.out.println("Number of visitors to the library: " + numberOfVisits);
        System.out.println("The average amount of time spent at the library for a visit: "
    + ((lengthOfTime / numberOfVisits) / 60 / 1000) + " minutes");
        System.out.println("The books purchased for the specified month: ");
        System.out.println("The amount of money collected through checked out book fines: ");
*/
        /*
            [ ] The number of books currently owned by the library.
            [X] The number of visitors of the library.
            [X] The average amount of time spent at the library for a visit.
            [ ] The books purchased for the specified month.
            [ ] The amount of money collected through checked out book fines
         */
    // }

    /*public int visitorCheckIn(Visitor visitor, Book book) {
        int cost = -1;
        ArrayList<CheckedOut> allCheckouts = visitor.getCheckouts();
        for (CheckedOut checkedOut: allCheckouts) {
            if (checkedOut.getISBN() == book.getISBN()) {
                Inventory.get(book.getISBN()).checkinBook();
                cost = visitor.checkInBook(checkedOut);
            }
        }
        return cost;
    }*/

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

}