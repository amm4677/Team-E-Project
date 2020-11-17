package Requests;

import Resposes.DateTimeResponse;
import Resposes.Response;
import main.Models.Libraries.LibraryBase;
import main.Models.TimeManager;

import java.sql.Time;
import java.util.Date;

/**
 * represents the request for the date and time of the library. One of the concrete commands in our command pattern and a part of our Mediator pattern
 *
 * @author Joseph Saltalamacchia
 */
public class DateTimeRequest implements Request{

    TimeManager proxyTimeManager;

   public DateTimeRequest(TimeManager timeManager){
       proxyTimeManager = timeManager;
   }

    @Override
    public Response performRequest() {
        return new DateTimeResponse(proxyTimeManager.toString());
    }
}
