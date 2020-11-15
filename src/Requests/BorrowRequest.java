package Requests;

import Resposes.BorrowResponse;
import Resposes.Response;
import main.Models.OwningLibrary;

import java.util.ArrayList;

public class BorrowRequest implements Request {

    private static final RequestNames.RequestName COMMAND = RequestNames.RequestName.BORROW;

    OwningLibrary library;
    private long ISBN;
    private long visitorID;
    private String invalidID;


    /**
     * contructor for the request to borrow a book
     * //todo change this to work with multiple books
     * @param library the library the books being borrowed are coming from
     * @param parameters
     */
    public BorrowRequest(OwningLibrary library, ArrayList<String> parameters){

        if(parameters.size() == 3){

            String stringID = parameters.get(1);

            this.library = library;
            if(Validator.validateAndParseLong(stringID) == -1){
                invalidID = stringID;
            }
            visitorID = Validator.validateAndParseLong(stringID);

            ISBN = Validator.validateAndParseLong(parameters.get(2));
        }
    }
    @Override
    public Response performRequest() {

        if(this.visitorID == -1 || !(library.getRegister().containsKey(visitorID))){
            return new BorrowResponse(invalidID);
        }
        if(!library.containsBook(ISBN)){
            return new BorrowResponse(ISBN);
        }
        if(!(library.getRegister().get(visitorID).canCheckOutBook())){
            return new BorrowResponse();
        }
        if(!(library.getRegister().get(visitorID).owesFine())){
            return new BorrowResponse(library.getRegister().get(visitorID).getTotalFines());
        }

        library.borrowBook(visitorID, ISBN);
        return new BorrowResponse(123);

    }
}
