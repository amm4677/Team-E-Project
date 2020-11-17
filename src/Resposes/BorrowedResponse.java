package Resposes;

import Requests.RequestNames;
import main.Models.Book;

import java.util.List;

public class BorrowedResponse implements Response{

    private static final RequestNames.RequestName COMMAND = RequestNames.RequestName.BORROWED;

    String responseMessage;

    public BorrowedResponse(){
        responseMessage = COMMAND + "The visitor ID does not match a registered Visitor";
    }

    public BorrowedResponse(List<Book> booksBorrowed){

        responseMessage = COMMAND.toString() + booksBorrowed.size();
        int index = 0;

        for(Book book : booksBorrowed){
            responseMessage = responseMessage + "\n" + index + ", " +
                    book.getISBN() + ", " + book.getTitle();
        }
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
