package Resposes;

import Requests.RequestNames;

public class PartialResponse implements Response {

    private final RequestNames.RequestName COMMAND = RequestNames.RequestName.PARTIAL_REQUEST;

    @Override
    public RequestNames.RequestName getCommand() {
        return COMMAND;
    }

    @Override
    public String getResponse(){
        return("partial-request");
    }
}
