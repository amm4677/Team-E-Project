package Resposes;

import Requests.EndVisitRequest;
import Requests.RequestNames;
import main.Models.Visit;

/**
 * represents the response the request to end a visit to the library. One of the concrete commands in our command pattern and a part of our Mediator pattern
 *
 * @author Joseph Saltalamacchia
 */
public class EndVisitResponse implements Response{

    private static final RequestNames.RequestName COMMAND = RequestNames.RequestName.DEPART;

    String responseMessage;

    /**
     * in the case of an invalid ID, inform the user that the id is invalid
     * @param invalidID the invalid ID
     */
    public EndVisitResponse(String invalidID){
        responseMessage = COMMAND + "invalid ID: " + invalidID;
    }

    public EndVisitResponse(Visit visit){
        String completeMessage = "Visitor ID: " + visit.getVisitorID()+ ", end time: " + visit.getEndTime() +
                ", visit durration: " + visit.getFormatedLengthOfVisit();

        responseMessage = COMMAND + completeMessage;
    }


    @Override
    public RequestNames.RequestName getCommand() {
        return COMMAND;
    }

    @Override
    public String getResponse() {
        return responseMessage;
    }
}
