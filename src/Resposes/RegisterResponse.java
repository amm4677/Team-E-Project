package Resposes;

import Requests.RequestNames;

import java.util.Calendar;
import java.util.Date;

/**
 * represents the response the request register a visitor to the library. One of the concrete commands in our command pattern and a part of our Mediator pattern
 *
 * @author Joseph Saltalamacchia
 */
public class RegisterResponse implements Response {

    private final RequestNames.RequestName RESPONSE_NAME = RequestNames.RequestName.REGISTER;

    private Long visitorID;
    private String registrationDate;

    boolean duplicate;

    /**
     * If a visitor was successfully added to the library this contrustor will be used
     * @param visitorID the ID number of the visitor
     */
    public RegisterResponse(Long visitorID) {
        this.visitorID = visitorID;
        registrationDate = new Date().toString();
        duplicate = false;
    }

    /**
     * the constructor that will be used if the user attempting to be registered was already in the register
     */
    public RegisterResponse() {
        duplicate = true;
    }

    @Override
    public RequestNames.RequestName getCommand() {
        return this.getCommand();
    }

    @Override
    public String getResponse() {
        if(duplicate){
            return("Register >> duplicate");
        }

        return ("Register >> Visitor ID: " + visitorID.toString() + ", Registration Date: " + registrationDate);
    }


}
