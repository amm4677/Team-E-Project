package Requests;


import Resposes.Response;

/**
 * the navtive interfacce for the requests and the base of the command pattern
 *
 *  @author Joseph Saltalamacchia
 */
public interface Request {

    public Response performRequest();


}
