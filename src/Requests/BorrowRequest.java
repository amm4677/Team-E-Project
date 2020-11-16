package Requests;

import Resposes.BorrowResponse;
import Resposes.Response;
import main.Models.Libraries.LibraryBase;
import main.Models.OwningLibrary;

import java.util.ArrayList;

/**
 * represents the request to borrow a book. One of the concrete commands in our command pattern and a part of our Mediator pattern
 *
 * @author Joseph Saltalamacchia
 */

public class BorrowRequest implements Request {

    private static final RequestNames.RequestName COMMAND = RequestNames.RequestName.BORROW;

    LibraryBase libraryProxy;
    private long ISBN;
    private long visitorID;
    private String invalidID;


    /**
     * contructor for the request to borrow a book
     * //todo change this to work with multiple books
     * @param library the library the books being borrowed are coming from
     * @param parameters
     */
    public BorrowRequest(LibraryBase library, ArrayList<String> parameters){

        if(parameters.size() == 3){

            String stringID = parameters.get(1);

            this.libraryProxy = library;
            if(Validator.validateAndParseLong(stringID) == -1){
                invalidID = stringID;
            }
            visitorID = Validator.validateAndParseLong(stringID);

            ISBN = Validator.validateAndParseLong(parameters.get(2));
        }
    }
    @Override
    public Response performRequest() {

        if(this.visitorID == -1 || !(libraryProxy.getRegister().containsKey(visitorID))){
            return new BorrowResponse(invalidID);
        }
        if(!libraryProxy.containsBook(ISBN)){
            return new BorrowResponse(ISBN);
        }
        if(!(libraryProxy.getRegister().get(visitorID).canCheckOutBook())){
            return new BorrowResponse();
        }
        if(!(libraryProxy.getRegister().get(visitorID).owesFine())){
            return new BorrowResponse(libraryProxy.getRegister().get(visitorID).getTotalFines());
        }

        libraryProxy.borrowBook(visitorID, ISBN);
        return new BorrowResponse(123);

    }
}
