package main.Models.Libraries;

import main.Models.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class LibraryBase {

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
    public abstract void addBook(Book book, int copies);
    public abstract void addVisitor(Visitor visitor);
    public abstract Visit startVisit(int visitorID);
    public abstract void endVisit(int visitorID);
    public abstract boolean visitorCheckOut(Visitor visitor, Long ISBN);

    public void closeLibrary() {

        writeVisitors();
        writeBooks();

        //Checks everyone out of the library for the day and writes a record of it
        for (Visit v : Visits) {
            if (v.getIsOngoingVisit()) v.endVisit(time.getDate());
        }
        writeVisits();
        Visits.clear();
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


    //=====================================================================================================================
    //==================================================Readers============================================================
    //=====================================================================================================================


    /**
     * A private helper method to read all of the saved Books from the file they were saved on
     */
    protected void readBooks() {
        try {
            FileInputStream fBook = new FileInputStream(new File("TextFiles/BookLog.bin"));
            ObjectInputStream oBook = new ObjectInputStream(fBook);

            boolean keepReading = true;
            try {
                while (keepReading) {
                    LibraryEntry book = (LibraryEntry) oBook.readObject();
                    this.Inventory.put(book.getISBN(), book);
                }
            } catch (EOFException ignored) {
            }

            fBook.close();
            oBook.close();

        } catch (FileNotFoundException f) {
            System.out.println("BookLog file not found");
        } catch (IOException i) {
            System.out.println("No Books In library");
        } catch (ClassNotFoundException c) {
            System.out.println("could not find class");
        }
    }


    /**
     * A private helper method to read all of the saved Visitors from the file they were saved on
     */
    protected void readVisitors() {
        try {
            FileInputStream fVisitor = new FileInputStream(new File("TextFiles/VisitorLog.bin"));
            ObjectInputStream oVisitor = new ObjectInputStream(fVisitor);

            boolean keepReading = true;
            try {
                while (keepReading) {
                    Visitor visitor = (Visitor) oVisitor.readObject();
                    this.Register.put(visitor.getID(), visitor);
                    // oVisitor = new ObjectInputStream(fVisitor);
                }
            } catch (EOFException ignored) {
            }

            fVisitor.close();
            oVisitor.close();

        } catch (FileNotFoundException f) {
            System.out.println("VisitorLog file not found");
        } catch (IOException i) {
            System.out.println("No Visitors registered in library");
        } catch (ClassNotFoundException c) {
            System.out.println("could not find class");
        }
    }


    //=====================================================================================================================
    //==================================================Writers============================================================
    //=====================================================================================================================


    /**
     * private helper method to print Visitors out to a file
     */
    protected void writeBooks() {
        try {
            //create a writer for the Books
            FileOutputStream fBook = new FileOutputStream(new File("TextFiles/BookLog.bin"));
            ObjectOutputStream oBook = new ObjectOutputStream(fBook);

            Iterator BookIterator = Inventory.entrySet().iterator();

            //print each visitor to the file
            while (BookIterator.hasNext()) {
                Map.Entry pair = (Map.Entry) BookIterator.next();
                LibraryEntry b = (LibraryEntry) pair.getValue();

                oBook.writeObject(b);
                System.out.println(b.toString());
                BookIterator.remove();
            }

            fBook.close();
            oBook.close();

        } catch (FileNotFoundException f) {
            System.out.println("Book File Not Found");
        } catch (IOException i) {
            System.out.println("Error initializing stream");
        }
    }

    /**
     * private helper method to print Visitors out to a file
     */
    protected void writeVisitors() {
        try {
            //create a writer for the visitors
            FileOutputStream fVisitor = new FileOutputStream(new File("TextFiles/VisitorLog.bin"));
            ObjectOutputStream oVisitor = new ObjectOutputStream(fVisitor);

            Iterator VisitorIterator = Register.entrySet().iterator();

            //print each visitor to the file
            while (VisitorIterator.hasNext()) {
                Map.Entry pair = (Map.Entry) VisitorIterator.next();
                Visitor v = (Visitor) pair.getValue();

                oVisitor.writeObject(v);

                System.out.println(v.toString());
                VisitorIterator.remove();
            }

        } catch (FileNotFoundException f) {
            System.out.println("Visitor File Not Found");
        } catch (IOException i) {
            System.out.println("Error initializing stream");
        }
    }

    protected void writeVisits() {
        try {
            //create a writer for the daily visits
            FileOutputStream fTime = new FileOutputStream(new File("TextFiles/VisitLog-"
                    + time.getFormattedDate() + ".bin"));
            ObjectOutputStream oTime = new ObjectOutputStream(fTime);

            //writes the time object into the file
            oTime.writeObject(Visits);

        } catch (FileNotFoundException f) {
            System.out.println("Visit log file Not Found");
        } catch (IOException i) {
            System.out.println("Error initializing stream");
        }
    }
}
