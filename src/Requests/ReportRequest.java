package Requests;

import Resposes.RegisterResponse;
import Resposes.Response;
import main.Models.OwningLibrary;
import main.Models.Visitor;

import java.util.ArrayList;

/**
 * Represents the "Statistics" request, reports library info
 */

public class ReportRequest implements Request{

    RequestNames.RequestName Command = RequestNames.RequestName.REPORT;

    //a proxy for the library that the Visitor is being added to
    private OwningLibrary proxyLibrary;

    public ReportRequest() {
        //Can be empty
    }

    @Override
    public Response performRequest() {
        return null;
    }
}
