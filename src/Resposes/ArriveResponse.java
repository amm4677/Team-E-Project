package Resposes;

import Requests.RequestNames;
import main.Models.Visit;

/**
 * represents the response the request to begin a visit to the library. One of the concrete commands in our command pattern and a part of our Mediator pattern
 *
 * @author Joseph Saltalamacchia
 */
public class ArriveResponse implements Response {

    private static final RequestNames.RequestName COMMAND = RequestNames.RequestName.ARRIVE;

    private String responseMessage;
    /**
     * constructor for the case that the visitor id entered in the request was invalid
     * @param invalidID : the invalid ID
     */
    public ArriveResponse(String invalidID){
        responseMessage = "invalid ID: " + invalidID;
    }

    /**
     * constructor for the case that the visitor entered was a duplicate
     */
    public ArriveResponse(){
        responseMessage = "duplicate";
    }

    /**
     * constructor for the case that the visit was successful
     * @param newVisit the new visit started
     */
    public ArriveResponse(Visit newVisit){
        responseMessage = newVisit.toString();
    }

    @Override
    public RequestNames.RequestName getCommand() {
        return COMMAND;
    }

    @Override
    public String getResponse() {
        return (COMMAND + ", " + responseMessage);
    }
}
