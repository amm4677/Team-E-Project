package Resposes;

import Requests.RequestNames;

public class BorrowResponse implements Response{

    private static final RequestNames.RequestName COMMAND = RequestNames.RequestName.BORROW;

    String responseMessage;

    /**
     * contrustor for the case that the Visitor or Book ID is not valid
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

    public BorrowResponse(int num){
        responseMessage = ((Integer)num).toString();
    }
    @Override
    public RequestNames.RequestName getCommand() {
        return null;
    }

    @Override
    public String getResponse() {
        return (COMMAND + ", " + responseMessage);
    }
}
