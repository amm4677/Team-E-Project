package Resposes;

import Requests.RequestNames;

public class DateTimeResponse implements Response {

    private static final RequestNames.RequestName COMMAND = RequestNames.RequestName.DATETIME;
    String responseMessage;

    public DateTimeResponse(String DateTime){
        responseMessage = "DateTime >> " + DateTime;
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
