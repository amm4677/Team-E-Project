package Requests;

import Resposes.BuyResponse;
import Resposes.Response;
import main.Models.Book;
import main.Models.OwningLibrary;

import java.util.ArrayList;
import java.util.HashMap;

public class BuyRequest implements Request {

    private static final RequestNames.RequestName COMMAND = RequestNames.RequestName.BUY;

    OwningLibrary libraryProxy;
    HashMap<Long, Book> bookstoreProxy;
    int quantity;
    ArrayList<Long> ids;
    StringBuilder booksAdded;

    public BuyRequest(OwningLibrary library,  HashMap<Long, Book> bookstore, ArrayList<String> Parameters) {
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
                System.out.println(tempID);
                System.out.println(ids);
                ids.add(tempID);
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

            libraryProxy.addBook(bookToAdd, quantity);

            booksAdded.append(bookToAdd.toString() + ", ");
        }

        //removes the last two appended characters (the last ", ")
        booksAdded.deleteCharAt(booksAdded.length()-1);
        booksAdded.deleteCharAt(booksAdded.length()-1);

        return new BuyResponse(booksAdded.toString());



    }
}
