package Resposes;

import Requests.RequestNames;

public class ReportResponse implements Response{

    private static final RequestNames.RequestName COMMAND = RequestNames.RequestName.REPORT;

    String responseMessage;

    public ReportResponse(String report){
        responseMessage = COMMAND + ">> " + report;
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
