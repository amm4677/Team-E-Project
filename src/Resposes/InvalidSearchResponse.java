package Resposes;

import Requests.RequestNames;

public class InvalidSearchResponse implements Response {
    private final RequestNames.RequestName RESPONSE_NAME = RequestNames.RequestName.INFO;

    @Override
    public RequestNames.RequestName getCommand() {
        return RESPONSE_NAME;
    }

    @Override
    public String getResponse() {
        return "info,invalid-sort-order";
    }
}
