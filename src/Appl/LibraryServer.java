package Appl;

import Requests.*;

import Resposes.*;


import main.Models.Book;
import main.Models.Libraries.ClosedLibrary;
import main.Models.Libraries.LibraryBase;
import main.Models.Libraries.LibrarySaveAndRead;
import main.Models.Libraries.OpenLibrary;
import main.Models.TimeManager;
import main.Models.*;
import View.MyFrame;

import javax.swing.*;
import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.*;
import java.util.Scanner;

/**
 * The server and main point of interaction for the user. Maintains the Library model and monitors the time.
 * Acts as the controler for the system
 *
 * @author Joseph Saltalamacchia
 */
public class LibraryServer {

    private static HashMap<Long, Book> bookStore;
    private static LibraryBase library;

    private static TimeManager timeManager;
    private static final LocalTime OPENING_TIME = LocalTime.of(8, 0, 0);
    private static final LocalTime CLOSING_TIME = LocalTime.of(19, 0, 0);

    public static final String BOOKSFILE = "TextFiles/Books.txt";

    public static Boolean isRunning = true;

    private static long lastBookStoreSearchID;
    private static long lastLibrarySearchID;

    public static void main(String[] args) {

       // timeManager = TimeManager.getInstance();
        LibraryServer.readTime();
        library = openLibrary();
        LibraryEntry mostRecentSearch = null;

        //sets up library to be open or closed depending on time
        checkLibraryStatus();
        //opens the library

        bookStore = new HashMap<Long, Book>();

        // Scanner reader = new Scanner(new File(BOOKSFILE))

        try {
            bookStore = CSVBookParser.CreateBooks(new File(BOOKSFILE));
        } catch (FileNotFoundException f) {
            System.out.println("Could dont Find Books file");
        }

        lastLibrarySearchID = -1;
        lastBookStoreSearchID = -1;
        //********** Checks if open and prints status
       // System.out.println(library.checkIfOpen());
        //********** Forces the library into a new OpenLibrary Class
        //OpenLibrary libraryOpen = new OpenLibrary(library.forceOpen());
       // library = libraryOpen;
        //********** Reprints status
       // System.out.println(library.checkIfOpen());
        //**********

        MyFrame frame = new MyFrame();
        frame.setVisible(true);

        //Save Library
        //End Application
        closeLibrary(library);
        writeTime();

        //used to test that the system worked
        //testPersistence(library);

    }

    private static LibraryBase openLibrary() {
        return LibrarySaveAndRead.openLibrary();
    }

    private static boolean closeLibrary(LibraryBase library) {
        return LibrarySaveAndRead.saveLibrary(library);
    }

    public static Response getSystemResponse(ArrayList<String> parameters) {
        Request userRequest = new DefualtRequest();
        Response systemResponse = userRequest.performRequest();

        //ensures that commands are not case sensitive
        String command = parameters.get(0).toLowerCase().trim();

      checkLibraryStatus();
      
        switch (command) {
            case "quit":
                isRunning = false;
                break;
            case "save":
                closeLibrary(library);
                writeTime();
                systemResponse = new SaveResponse();
                break;
            case "register":
                if (parameters.size() == 5) {
                    userRequest = new RegisterRequest(library, parameters);
                    systemResponse = userRequest.performRequest();
                }
                break;
            case "arrive":
                if (parameters.size() == 2) {
                    userRequest = new ArriveRequest(library, parameters);
                    systemResponse = userRequest.performRequest();
                }
                break;
            case "depart":
                if (parameters.size() == 2) {
                    userRequest = new EndVisitRequest(library, parameters);
                    systemResponse = userRequest.performRequest();
                }
                break;
            case "info":
                //searching the library's inventory
                userRequest = new InfoRequest(library, parameters);
                systemResponse = userRequest.performRequest();
                //todo: this is terrible design and needs to be refactored
                lastLibrarySearchID = ((InfoResponse)systemResponse).getFirstBookID();

                break;
            case "search":
                //searching the bookstore's inventory
                if (parameters.size() > 2) {
                    userRequest = new SearchRequest(bookStore.values(), parameters);
                    systemResponse = userRequest.performRequest();
                    lastBookStoreSearchID = ((SearchResponse)systemResponse).getFirstBookID();
                }
                break;
            case "borrow":
                System.out.println(lastLibrarySearchID);
                if (parameters.size() == 3 || (parameters.size() == 2 && lastLibrarySearchID != -1)) {
                    if(lastLibrarySearchID != -1){
                        parameters.add(((Long)lastBookStoreSearchID).toString());
                    }
                    userRequest = new BorrowRequest(library, parameters, timeManager);
                    systemResponse = userRequest.performRequest();
                }
                break;
            case "buy":
                if (parameters.size() >= 3 || (parameters.size() == 2 && lastBookStoreSearchID != -1)) {
                    if(lastBookStoreSearchID != -1){
                        parameters.add(((Long)lastBookStoreSearchID).toString());
                    }
                    userRequest = new BuyRequest(library, bookStore, parameters);
                    systemResponse = userRequest.performRequest();
                }
                break;
            case "datetime":
                if (parameters.size() == 1) {
                    userRequest = new DateTimeRequest(timeManager);
                    systemResponse = userRequest.performRequest();
                }
                break;
            case "advance":
                if (parameters.size() == 3) {
                    userRequest = new AdvanceTimeRequest(library, timeManager,  parameters);
                    systemResponse = userRequest.performRequest();
                }
                break;
            case "borrowed":
                if(parameters.size() ==2){
                    userRequest = new BorrowedRequest(library, parameters);
                    systemResponse = userRequest.performRequest();
                }
            case "report":
                userRequest = new ReportRequest(library);
                systemResponse = userRequest.performRequest();
                break;
            case "return":

            default:
                System.out.println("Invalid command, please try again");
                break;
        }//end switch

        return systemResponse;
    }

    private static void readTime() {
        try {
            FileInputStream fTime = new FileInputStream(new File("TextFiles/TimeLog.bin"));
            ObjectInputStream oTime = new ObjectInputStream(fTime);

            try {
                //Use file to create TimeManager
                String[] timeInfo = ((String) oTime.readObject()).split(" ");
                timeManager = TimeManager.createInstance(timeInfo[0], timeInfo[1]);
            } catch (EOFException ignored) {
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println(e);
            }

            fTime.close();
            oTime.close();

        } catch (FileNotFoundException f) {
            //if no file, create a new TimeManager
            timeManager = TimeManager.getInstance();
        } catch (IOException i) {
            System.out.println("Error initializing stream");
        } catch (ClassNotFoundException c) {
            System.out.println("could not find class");
        }
    }

    private static void writeTime() {
        try {
            //create a writer for the visitors
            FileOutputStream fTime = new FileOutputStream(new File("TextFiles/TimeLog.bin"));
            ObjectOutputStream oTime = new ObjectOutputStream(fTime);

            //writes the time object into the file
            oTime.writeObject(timeManager.getFormattedDate() + " " + timeManager.getFormattedTime());

        } catch (FileNotFoundException f) {
            System.out.println("Time File Not Found");
        } catch (IOException i) {
            System.out.println("Error initializing stream");
        }
    }

    private static void checkLibraryStatus() {

        int hours = timeManager.getHours();
        int minutes = timeManager.getMinutes();
        int seconds = timeManager.getSeconds();

        LocalTime currentTime = LocalTime.of(hours, minutes, seconds);

        int openCompare = currentTime.compareTo(OPENING_TIME);
        int closeCompare = currentTime.compareTo(CLOSING_TIME);

        //can't have a null library, make closed with a status of default so it can be overridden by one of the two cases.
        if (library == null) {
            library = new ClosedLibrary();
            library.libraryStatus = LibraryBase.LibraryStatus.Default;
        }

        //if within opening and closing hours and library is not open, make an OpenLibrary()
        if ((openCompare >= 0) && (closeCompare < 0) && (library instanceof ClosedLibrary)) {
            library = new OpenLibrary(library);
        }
        //else if the library is outside the bounds of opening and closing time and is not closed, make a ClosedLibrary()
        else if ((openCompare < 0) && (closeCompare >= 0) && (library instanceof OpenLibrary)) {
            library = new ClosedLibrary(library);
            library.closeVisits();
        }
    }
}


    //test to ensure that system persistence works
/*
    private static void testPersistence(OwningLibrary library){
        Book book1 = new Book(123456, "Bacon", "JK Rowling", "BookyBois Inc", new Date().toString(), 500, 3);
        Book book2 = new Book(789456, "Bits", "HP Lovecraft", "Bookinator LTD", new Date().toString(), 400, 4);
        Book book3 = new Book(564231, "United","Jim Butcher", "The Other One inc.",  new Date().toString(),  5, 1000);

        library.addBook(book1,1);
        library.addBook(book2,1);
        library.addBook(book3,1);

        //String firstName, String lastName, String address, int phoneNumber, int ID, ArrayList<Book> booksCheckedOut

        Visitor v1 = new Visitor("Sherlock", "Holms", "221B Baker Street", "1234567891", 1);
        Visitor v2 = new Visitor("Howard", "Lovecraft", "454 Angell Street", "999999999", 2);
        Visitor v3 = new Visitor("Dory", "Just Dory", "P. Sherman 42 Wallaby Way", "1131131131", 3);

        library.addVisitor(v1);
        library.addVisitor(v2);
        library.addVisitor(v3);

        library.closeLibrary();


        System.out.println("==============================================================================");
        System.out.println("==============================================================================");
        System.out.println("==============================================================================");

        OwningLibrary testLibrary = new OwningLibrary();

        testLibrary.openLibrary();

        System.out.println(testLibrary.toString());
    }*/
