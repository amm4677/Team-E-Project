package Resposes;

import Requests.RequestNames;

public class SaveResponse implements Response {
    private final RequestNames.RequestName COMMAND = RequestNames.RequestName.SAVE;


    @Override
    public RequestNames.RequestName getCommand() {
        return COMMAND;
    }

    @Override
    public String getResponse() {
        return "The Library Management System has been successfully saved!";
    }
}
