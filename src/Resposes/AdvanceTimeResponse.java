package Resposes;

import Requests.AdvanceTimeRequest;
import Requests.RequestNames;

public class AdvanceTimeResponse implements Response {

    private static final RequestNames.RequestName COMMAND = RequestNames.RequestName.ADVANCE;


    String responseMessage;

    /**
     * The successful case
     */
    public AdvanceTimeResponse(){
        responseMessage = COMMAND + " >> success";
    }

    public AdvanceTimeResponse(AdvanceTimeRequest.DayHour DayOrHour, int badnumber){
        if(DayOrHour == AdvanceTimeRequest.DayHour.DAY){
            responseMessage = COMMAND + " >> invalid number of days, " + badnumber;
        }
        if(DayOrHour == AdvanceTimeRequest.DayHour.HOUR){
            responseMessage = COMMAND + " >> invalid number of hours, " + badnumber;
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
