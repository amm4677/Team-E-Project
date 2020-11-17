package Requests;

import Resposes.BorrowedResponse;
import Resposes.Response;
import main.Models.Book;
import main.Models.Libraries.LibraryBase;
import main.Models.LibraryEntry;
import main.Models.Visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * represents the request to get the list of books a visitor has checked out. One of the concrete commands in our command pattern and a part of our Mediator pattern
 *
 * @author Joseph Saltalamacchia
 */
public class BorrowedRequest implements Request {

    private static final RequestNames.RequestName COMMAND = RequestNames.RequestName.BORROWED;


    LibraryBase libraryProxy;
    long visitorID;

    /**
     * Constructor for the Borrowed request
     * @param library the library being queried
     * @param parameters two values, the first being the command, the second being the visitor ID
     */
    public BorrowedRequest(LibraryBase library, ArrayList<String> parameters){
        libraryProxy = library;
        visitorID = Validator.validateAndParseLong(parameters.get(1));
    }

    @Override
    public Response performRequest() {

        //the id entered is valid
        if(libraryProxy.getRegister().containsKey(visitorID)){
            Visitor guest = libraryProxy.getRegister().get(visitorID);
            List<Book> booksCheckedOut = guest.getBooksBorrowed();

            return new BorrowedResponse(booksCheckedOut);
        }

        //if we got here the visitorID entered was not valid
        return new BorrowedResponse();
    }
}
