package Resposes;

import Requests.RequestNames;
import main.Models.Book;
import main.Models.LibraryEntry;

import java.util.ArrayList;

public class SearchResponse implements Response {
    private final RequestNames.RequestName RESPONSE_NAME = RequestNames.RequestName.SEARCH;

    private ArrayList<Book> books;

    public SearchResponse(ArrayList<Book> books) {
        this.books = books;
    }

    @Override
    public RequestNames.RequestName getCommand() {
        return RESPONSE_NAME;
    }

    @Override
    public String getResponse() {
        StringBuilder builder = new StringBuilder();

        builder.append("search,");
        builder.append(books.size());

        int temp_id = 0;
        for (Book b : books) {
            builder.append(",\n");
            builder.append(temp_id);
            builder.append(",");
            builder.append(b.getISBN());
            builder.append(",");
            builder.append("\"" + b.getTitle() + "\", ");
            builder.append(b.getAuthors());

            temp_id++;
        }

        return builder.toString();
    }
}
