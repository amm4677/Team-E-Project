package Resposes;

import Requests.DefualtRequest;
import Requests.RequestNames;

/**
 * the default response, details what the other appropriate requests are
 */
public class DefaultResponse implements Response {

    private static final RequestNames.RequestName COMMAND = RequestNames.RequestName.DEFAULT;

    public DefaultResponse(){
        //nothing to see here
    }

    @Override
    public RequestNames.RequestName getCommand() {
        return COMMAND;
    }

    @Override
    public String getResponse() {
        return "Invalid Request!" +
                "\nValid Requests include the following:" +
                "\nRegister: firstName, LastName, address, Phone-Number" +
                "\nArrive: VisitorID" +
                "\nDepart: VisitorID" +
                "\nInfo: title, {authors}, ISBN, publisher, Sort-Order;  (any parameter can be replaced by an '*'" +
                "\nBorrow: VisitorID, [id] (where [id] is a comma seperated list of book ids" +
                "\nBorrowed: VisitorID" +
                "\nReturn: VisitorID, id" +
                "\nPay: VisitorID, ammount" +
                "\nSearch: title, {authors}, ISBN, publisher, Sort-Order;  (any parameter can be replaced by an '*'" +
                "\nBuy: quantity, id, [ids]; (where id is the last book searched, and [ids] is a comma seperated list of other books" +
                "\nAdvance: number-of-days, number-of-hours; (where days is between 0 and 7, and hours is between 0 and 23)" +
                "\nDateTime" +
                "\nReport: days" +
                "\nSave";
    }
}
