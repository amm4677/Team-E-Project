package Resposes;

import Requests.RequestNames;
import main.Models.TimeManager;

import java.util.Calendar;
import java.util.Date;

/**
 * represents the response the request register a visitor to the library. One of the concrete commands in our command pattern and a part of our Mediator pattern
 *
 * @author Joseph Saltalamacchia
 */
public class RegisterResponse implements Response {

    private final RequestNames.RequestName RESPONSE_NAME = RequestNames.RequestName.REGISTER;

    String responseMessage;

    /**
     * If a visitor was successfully added to the library this constructor will be used
     * @param visitorID the ID number of the visitor
     */
    public RegisterResponse(Long visitorID) {
        String registrationDate = TimeManager.getInstance().getLocalTime().toString();

        responseMessage = ("Register >> Visitor ID: " + visitorID.toString() + ", Registration Date: " + registrationDate);
    }

    /**
     * the constructor that will be used if the user attempting to be registered was already in the register
     */
    public RegisterResponse() {
        responseMessage = "Register >> duplicate";
    }

    /**
     * This constructor will only ever be reached if the library was closed
     * @param failedToRegister ignored
     */
    public RegisterResponse(boolean failedToRegister){
        responseMessage = "Register >> cannot register a user while the library is closed";
    }

    @Override
    public RequestNames.RequestName getCommand() {
        return this.getCommand();
    }

    @Override
    public String getResponse() {
        return (responseMessage);
    }


}
