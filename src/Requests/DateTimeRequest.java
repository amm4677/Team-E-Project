package Requests;

import Resposes.DateTimeResponse;
import Resposes.Response;
import main.Models.Libraries.LibraryBase;

import java.util.Date;

/**
 * represents the request for the date and time of the library. One of the concrete commands in our command pattern and a part of our Mediator pattern
 *
 * @author Joseph Saltalamacchia
 */
public class DateTimeRequest implements Request{

    LibraryBase proxyLibrary;

   public DateTimeRequest(LibraryBase library){
       proxyLibrary = library;
   }

    @Override
    public Response performRequest() {
        return new DateTimeResponse(proxyLibrary.getTime());
    }
}
