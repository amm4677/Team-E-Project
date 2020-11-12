package main.Models;

import java.io.*;
import java.util.Iterator;
import java.util.Map;

public class OpenLibrary  extends OwningLibrary {

    public OpenLibrary() {
        super();

        readVisitors();
        readBooks();
    }

    @Override
    public void closeLibrary() {

        super.closeLibrary();

        //Checks everyone out of the library for the day and writes a record of it
        for (Visit v : Visits) {
            if (v.getIsOngoingVisit()) v.endVisit(time.getDate());
        }
        writeVisits();
        Visits.clear();
    }





    //=====================================================================================================================
    //==================================================Readers and Writers================================================
    //=====================================================================================================================


    /**
     * A private helper method to read all of the saved Books from the file they were saved on
     */
    private void readBooks() {
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
    private void readVisitors() {
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

    private void writeVisits() {
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
