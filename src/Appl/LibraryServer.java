package Appl;

import Requests.*;
import Resposes.RegisterResponse;
import Resposes.Response;
import main.Models.Book;
import main.Models.OwningLibrary;
import main.Models.TimeManager;
import View.MyFrame;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.*;

/**
 *
 */
public class LibraryServer {

    private static OwningLibrary library;
    private static TimeManager timeManager;

    private static HashMap<Long, Book> bookStore;


    public static final String BOOKSFILE = "TextFiles/Books.txt";

    public static Boolean isRunning = true;

    public static void main(String[] args) {

        //opens the library
        library = new OwningLibrary(LocalTime.of(8, 0, 0),
                LocalTime.of(19, 0, 0));

        bookStore = new HashMap<Long, Book>();

        // Scanner reader = new Scanner(new File(BOOKSFILE))

        try {
            bookStore = CSVBookParser.CreateBooks(new File(BOOKSFILE));
        } catch (FileNotFoundException f) {
            System.out.println("Could Not Find Books File");
        }

        MyFrame frame = new MyFrame();
        frame.setVisible(true);

        //Save Library
        //End Application
        library.closeLibrary();

        //used to test that the system worked
        //testPersistence(library);*/
    }


   public static Response getSystemResponse(ArrayList<String> parameters) {
        Request userRequest;
        //todo: Need to change this to an actual default state
        Response systemResponse = new RegisterResponse();

        switch (parameters.get(0).toLowerCase().trim()) {
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
                userRequest = new InfoRequest(library, parameters);
                systemResponse = userRequest.performRequest();
                break;

            case "search":
                if (parameters.size() > 2) {
                    userRequest = new SearchRequest(bookStore.values(), parameters);
                    systemResponse = userRequest.performRequest();
                }
                break;
                case "borrow":
                        if (parameters.size() == 2) {
                            userRequest = new BorrowRequest(library, parameters);
                            systemResponse = userRequest.performRequest();
                        }
                        break;
                    case "buy":
                        if (parameters.size() >= 3) {
                            userRequest = new BuyRequest(library, bookStore, parameters);
                            systemResponse = userRequest.performRequest();
                        }
                        break;
                    default:
                        System.out.println("Invalid command, please try again");
                        break;
                }//end switch

                return systemResponse;
        }
    }

    /*
    private static ArrayList<String> splitCSV(String masterString) {
        ArrayList<String> arguments = new ArrayList<String>();

        //This could include matches with curly brackets within curly brackets. Filter them out and add them
        Pattern curlyBrackets = Pattern.compile("\\{(.*?)},");
        Matcher matcher = curlyBrackets.matcher(masterString);
        while(matcher.find()) {
            String s = matcher.group();
            String substring = s.substring(1, s.length() - 3);

            if(substring.contains("{") || substring.contains("}")) continue;

            arguments.add(substring);
            masterString = masterString.replace(s, "");
        }

        //Add remaining arguments
        for(String s : masterString.split(",")) {
            arguments.add(s);
        }

        return arguments;
    }
*/

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
