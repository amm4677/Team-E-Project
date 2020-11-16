package Requests;

import Resposes.RegisterResponse;
import Resposes.Response;
import main.Models.Libraries.LibraryBase;
import main.Models.OwningLibrary;
import main.Models.Visitor;

import java.util.ArrayList;

/**
 * Represents the "Statistics" request, reports library info
 */

//todo: just this entire thing
public class ReportRequest implements Request{

    RequestNames.RequestName Command = RequestNames.RequestName.REPORT;

    //a proxy for the library that the Visitor is being added to
    private LibraryBase proxyLibrary;

    public ReportRequest() {
        //Can be empty
    }

    @Override
    public Response performRequest() {
        return null;
    }
}
