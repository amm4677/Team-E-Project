package Requests;

import Resposes.ArriveResponse;
import Resposes.Response;
import main.Models.Libraries.LibraryBase;
import main.Models.OwningLibrary;
import main.Models.Visit;

import java.util.ArrayList;

/**
 * represents the request to begin a visit. One of the concrete commands in our command pattern and a part of our Mediator pattern
 *
 * @author Joseph Saltalamacchia
 */
public class ArriveRequest implements Request {

    private static final RequestNames.RequestName COMMAND = RequestNames.RequestName.ARRIVE;
    Long visitorID;
    String invalidID;
    LibraryBase library;

    public ArriveRequest(LibraryBase library, ArrayList<String> parameters){

        String stringID = parameters.get(1);

        this.library = library;

        visitorID = Validator.validateAndParseLong(stringID);

        if(visitorID == -1){
            invalidID = stringID;
        }
    }

    @Override
    /**
     * returns a response appropriate for the parameters entered in
     */
    public Response performRequest() {
        //if the ID entered was invalid, retruns the invalid ID
        if(visitorID ==-1){
            return new ArriveResponse(invalidID);
        }

        Visit tempVisit = library.startVisit(visitorID);

        //if the visitor is already visiting, inform the user that it's a duplicate
        if(tempVisit == null){
            return new ArriveResponse();
        }

        //otherwise show the user the new visit information
        return new ArriveResponse(tempVisit);
    }
}
