package Requests;

import Resposes.EndVisitResponse;
import Resposes.Response;
import main.Models.OwningLibrary;
import main.Models.Visit;

import java.util.ArrayList;

public class EndVisitRequest implements Request{

    private static final RequestNames.RequestName COMMAND = RequestNames.RequestName.DEPART;
    Long visitorID;
    String invalidID;
    OwningLibrary library;

    public EndVisitRequest(OwningLibrary library, ArrayList<String> parameters){
        String stringID = parameters.get(1);

        this.library = library;
        if(Validator.validateAndParseLong(stringID) == -1){
            invalidID = stringID;
        }
        visitorID = Validator.validateAndParseLong(parameters.get(1));
    }

    @Override
    public Response performRequest() {
        //if the ID entered was invalid, retruns the invalid ID
        if(visitorID ==-1){
            return new EndVisitResponse(invalidID);
        }

        Visit tempVisit = library.endVisit(visitorID);

        //if the visitor is already visiting, inform the user that it's a duplicate
        if(tempVisit == null){
            return new EndVisitResponse(invalidID);
        }

        //otherwise show the user the new visit information
        return new EndVisitResponse(tempVisit);
    }
}
