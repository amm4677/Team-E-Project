package Resposes;

import Requests.RequestNames;
import main.Models.LibraryEntry;

import java.util.ArrayList;

public class InfoResponse implements Response {
    private final RequestNames.RequestName RESPONSE_NAME = RequestNames.RequestName.INFO;

    private ArrayList<LibraryEntry> books;

    public InfoResponse(ArrayList<LibraryEntry> books) {
        this.books = books;
    }

    @Override
    public RequestNames.RequestName getCommand() {
        return RESPONSE_NAME;
    }

    @Override
    public String getResponse() {
        StringBuilder builder = new StringBuilder();

        builder.append("info,");
        builder.append(books.size());

        for (LibraryEntry e : books) {
            builder.append(",\n");
            builder.append(e.getBook().getTotalCopies() - e.getCopiesCheckedOut());
            builder.append(",");
            builder.append(e.getBook().getISBN());
            builder.append(",");
            builder.append("\"" + e.getBook().getTitle() + "\", ");
            builder.append(e.getBook().getAuthors());
        }

        return builder.toString();
    }
}
