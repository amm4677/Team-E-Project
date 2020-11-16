package Requests;

import Resposes.AdvanceTimeResponse;
import Resposes.Response;
import main.Models.Libraries.LibraryBase;

import java.util.ArrayList;

public class AdvanceTimeRequest implements Request {

    public enum DayHour{
        DAY,
        HOUR;
    }

    LibraryBase libraryProxy;
    ArrayList<String> parameters;

    public AdvanceTimeRequest(LibraryBase library, ArrayList<String> parameters){
        libraryProxy = library;
        this.parameters = parameters;
    }


    @Override
    public Response performRequest() {

        //the following two lines will always be valid because it is impossible to get here without "parameters" haing
        // exactly three entries

        //the second parameter should be the number of days to advance
        int days = Validator.validateAndParseInt(parameters.get(1));

        //the second parameter should be the number of days to advance
        int hours = Validator.validateAndParseInt(parameters.get(2));

        if(days < 0 || days > 7){
            //will return a "-1" is a non-number is entered
            return new AdvanceTimeResponse(DayHour.DAY, days);
        }
        else if(hours < 0 || hours > 23){
            //will return a "-1" is a non-number is entered
            return new AdvanceTimeResponse(DayHour.HOUR, hours);
        }

        libraryProxy.advanceTime(days, hours);
        return new AdvanceTimeResponse();
    }
}
