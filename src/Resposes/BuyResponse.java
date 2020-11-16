package Resposes;

import Requests.RequestNames;

/**
 * represents the response the request to buy a book for the library. One of the concrete commands in our command pattern and a part of our Mediator pattern
 *
 * @author Joseph Saltalamacchia
 */
public class BuyResponse implements Response{

    private static final RequestNames.RequestName COMMAND = RequestNames.RequestName.BUY;
    String responseMessage;

    //a failure case, for when the quantity entered is not a valid number
    public BuyResponse(){
        responseMessage = COMMAND + ", faliure, quantity not a valid number";
    }

    //a failure case, for when the quantity entered is not a valid number
    public BuyResponse(String booksBought){
        responseMessage = COMMAND + ", success, " + booksBought;
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
