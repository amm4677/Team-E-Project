package Requests;

import Resposes.BorrowResponse;
import Resposes.Response;
import main.Models.Libraries.LibraryBase;
import main.Models.OwningLibrary;
import main.Models.TimeManager;
import org.xml.sax.helpers.AttributesImpl;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * represents the request to borrow a book. One of the concrete commands in our command pattern and a part of our Mediator pattern
 *
 * @author Joseph Saltalamacchia
 */

public class BorrowRequest implements Request {

    private static final RequestNames.RequestName COMMAND = RequestNames.RequestName.BORROW;

    LibraryBase libraryProxy;
    TimeManager timeManagerProxy;
    private ArrayList<Long> ISBNs;
    private long visitorID;
    private String invalidID;


    /**
     * contructor for the request to borrow a book
     * @param library the library the books being borrowed are coming from
     * @param parameters
     */
    public BorrowRequest(LibraryBase library, ArrayList<String> parameters, TimeManager timeManager){

        timeManagerProxy = timeManager;
        String visitorStringID = parameters.get(1);

        this.libraryProxy = library;
        if(Validator.validateAndParseLong(visitorStringID) == -1){
            invalidID = visitorStringID;
        }
        visitorID = Validator.validateAndParseLong(visitorStringID);

        //get a full list of Book IDs
        List<String> allBookIDs = parameters.subList(2,parameters.size());

        ISBNs = new ArrayList<>();

        for(String bookID : allBookIDs) {

            long ID = Validator.validateAndParseLong(bookID);

            if(ID != -1) {
                ISBNs.add(ID);
            }
        }
    }
    @Override
    public Response performRequest() {

        if(this.visitorID == -1 || !(libraryProxy.getRegister().containsKey(visitorID))){
            return new BorrowResponse(invalidID);
        }
        else if(!(libraryProxy.getRegister().get(visitorID).canCheckOutBook())){

            int numberOfBooksCheckedOut = libraryProxy.getRegister().get(visitorID).getBooksBorrowed().size();

            if (numberOfBooksCheckedOut + ISBNs.size() > 5) {
                return new BorrowResponse();
            }
        }
        else if((libraryProxy.getRegister().get(visitorID).owesFine())){
            return new BorrowResponse(libraryProxy.getRegister().get(visitorID).getTotalFines());
        }

        //if all of the above passed, we can attempt to check out the books


        for(long ID : ISBNs) {
           /* if (!libraryProxy.containsBook(ID)) {
                libraryProxy.borrowBook(visitorID, ID);
            }*/
            libraryProxy.borrowBook(visitorID, ID);
        }
        //todo here dummy
        LocalDate dueDate = LocalDate.now().plusDays(7);
        return new BorrowResponse(1, dueDate.toString());

    }
}
