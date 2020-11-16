package Resposes;

import Requests.RequestNames;

/**
 * the navtive interfacce for the responses and the base of the command pattern
 *
 *  @author Joseph Saltalamacchia
 */
public interface Response {

    RequestNames.RequestName getCommand();

    String getResponse();
}
