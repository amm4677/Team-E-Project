package Resposes;

import Requests.RequestNames;

/**
 * represents the response the request to borrow a book from the library. One of the concrete commands in our command pattern and a part of our Mediator pattern
 *
 * @author Joseph Saltalamacchia
 */
public class BorrowResponse implements Response{

    private static final RequestNames.RequestName COMMAND = RequestNames.RequestName.BORROW;

    String responseMessage;

    /**
     * constructor for the case that the Visitor or Book ID is not valid
     * @param inValidID the invalid ID
     */
    public BorrowResponse(String inValidID){
        responseMessage = "invalid ID : " + inValidID;
    }

    public BorrowResponse(){
        responseMessage = "Book limit Exceeded";
    }

    public BorrowResponse(double fine){
        responseMessage = "Visitor has an outstanding fine of $" + fine;
    }

    public BorrowResponse(int num, String dueDate){
        responseMessage = dueDate;
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
