package Resposes;

import Requests.RequestNames;

public interface Response {

    RequestNames.RequestName getCommand();

    String getResponse();
}
