package Resposes;

import Requests.RequestNames;

public interface Response {

    public RequestNames.RequestName getCommand();

    public String getResponse();
}
