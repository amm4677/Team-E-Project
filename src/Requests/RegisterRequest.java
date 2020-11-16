package Requests;

import Resposes.RegisterResponse;
import Resposes.Response;
import main.Models.Libraries.LibraryBase;
import main.Models.Visitor;

import java.util.ArrayList;

/**
 * Represents the "register" request, adding a visitor to the provided libray. One of the concrete commands in our command pattern and a part of our Mediator pattern
 *
 * @author Joseph Saltalamacchia
 */
public class RegisterRequest implements Request {

    RequestNames.RequestName Command = RequestNames.RequestName.REGISTER;

    //a proxy for the library that the Visitor is being added to
    private LibraryBase proxyLibrary;

    //a counter to increment the Visitor's id every time a new one is created, ensuring that every ID is unique
    private static Long nextVisitorID = Long.valueOf(1000000000);

    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;

    /*
     * Constructor for a new "register" Request
     *

     * @param library the library reference
     * @param parameters Arraylist of all of the paramaters of the request
*/
    public RegisterRequest(LibraryBase library, ArrayList<String> parameters) {

        //todo do actual fact checking
        if(parameters.size() == 5) {
            this.firstName = parameters.get(1);
            this.lastName = parameters.get(2);
            this.address = parameters.get(3);
            this.phoneNumber = parameters.get(4);
        }
        proxyLibrary = library;
    }


    @Override
    public Response performRequest() {

        Visitor newVisitor = new Visitor(firstName,lastName,address,phoneNumber,nextVisitorID);

        //ensure that the visitor is not already registered
        if(proxyLibrary.getRegister().containsValue(newVisitor)){
            return new RegisterResponse();
        }

        //only increment the nextVisitorID when it is known that the visitor will be added
        nextVisitorID ++;
        proxyLibrary.addVisitor(newVisitor);
        return new RegisterResponse(newVisitor.getID());
    }
}
