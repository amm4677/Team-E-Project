package Requests;

import Resposes.BuyResponse;
import Resposes.Response;
import main.Models.Book;
import main.Models.Libraries.LibraryBase;
import main.Models.LibraryEntry;
import main.Models.OwningLibrary;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * represents the request to purchase a book from the bookstore for the library. One of the concrete commands in our command pattern and a part of our Mediator pattern
 *
 * @author Joseph Saltalamacchia
 */
public class BuyRequest implements Request {

    private static final RequestNames.RequestName COMMAND = RequestNames.RequestName.BUY;

    LibraryBase libraryProxy;
    HashMap<Long, Book> bookstoreProxy;
    int quantity;
    ArrayList<Long> ids;
    StringBuilder booksAdded;

    public BuyRequest(LibraryBase library,  HashMap<Long, Book> bookstore, ArrayList<String> Parameters) {
        this.libraryProxy = library;
        this.bookstoreProxy = bookstore;
        this.ids = new ArrayList<>();
        booksAdded = new StringBuilder();

        //the second parameter should always be the quantity of books being purchased
        this.quantity = Validator.validateAndParseInt(Parameters.get(1));

        //add all of the potential ID's to the list of book id's to be added
        for(int i = 2; i < Parameters.size(); i++){
            //attempt to parse any id's to a long. Value will be -1 if not a valid ID
            long tempID = Validator.validateAndParseLong(Parameters.get(i));

            //if the id is valid, add it to the list
            if(tempID != -1) {
                ids.add(tempID);
                System.out.println(tempID);
                System.out.println(ids);
            }
        }

    }

    @Override
    public Response performRequest() {

        if(quantity == -1){
            return new BuyResponse();
        }

        for(long id : ids){
            Book bookToAdd = bookstoreProxy.get(id);
            LibraryEntry addedBook = libraryProxy.addBook(bookToAdd, quantity);

            booksAdded.append(addedBook.toString() + ", ");
        }

        //removes the last two appended characters (the last ", ")
        booksAdded.deleteCharAt(booksAdded.length()-1);
        booksAdded.deleteCharAt(booksAdded.length()-1);

        return new BuyResponse(booksAdded.toString());



    }
}
