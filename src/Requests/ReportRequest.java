package Requests;

import Resposes.ReportResponse;
import Resposes.Response;
import main.Models.Libraries.LibraryBase;

/**
 * Represents the "Statistics" request, reports library info
 */

public class ReportRequest implements Request{

    RequestNames.RequestName Command = RequestNames.RequestName.REPORT;

    //a proxy for the library that the Visitor is being added to
    private LibraryBase proxyLibrary;

    public ReportRequest(LibraryBase library) {
        proxyLibrary = library;
    }

    @Override
    public Response performRequest() {

        String report = proxyLibrary.report();

        return new ReportResponse(report);
    }
}
