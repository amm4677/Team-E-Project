package Requests;

import Resposes.DefaultResponse;
import Resposes.Response;

/**
 * The default request type.
 */
public class DefualtRequest implements Request {

    private static final RequestNames.RequestName COMMAND = RequestNames.RequestName.DEFAULT;

    public DefualtRequest(){
        //nothing to see here
    }

    @Override
    public Response performRequest() {
        return new DefaultResponse();
    }
}
