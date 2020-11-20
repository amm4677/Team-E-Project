package Requests;

import Resposes.InfoResponse;
import Resposes.InvalidInfoResponse;
import Resposes.Response;
import main.Models.Libraries.LibraryBase;
import main.Models.LibraryEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

//todo: Dylan, please comment your code
public class InfoRequest implements Request {
    RequestNames.RequestName Command = RequestNames.RequestName.INFO;


    //a proxy for the library that the Visitor is being added to
    private LibraryBase proxyLibrary;

    private String title = "*";
    private String authors = "*";
    private String isbn = "*";
    private String publisher = "*";
    private String sortOrder = "title";


    public InfoRequest(LibraryBase library, ArrayList<String> parameters) {
        switch(parameters.size()) {
            default:
                break;
            case 6:
                this.sortOrder = parameters.get(5);
            case 5:
                this.publisher = parameters.get(4);
            case 4:
                this.isbn = parameters.get(3);
            case 3:
                this.authors = parameters.get(2);
                if(authors.startsWith("{")) authors = authors.substring(1);
                if(authors.endsWith("}")) authors = authors.substring(0, authors.length() - 1);
                this.title = parameters.get(1);
                break;
        }
        proxyLibrary = library;
    }

    @Override
    public Response performRequest() {
        ArrayList<LibraryEntry> books = new ArrayList<LibraryEntry>();

        if(!(sortOrder.equals("title") || sortOrder.equals("publish-date") || sortOrder.equals("book-status")))
            return new InvalidInfoResponse();


        for (LibraryEntry le : proxyLibrary.getInventory().values()) {
            if((title.equals("*") || le.getBook().getTitle().contains(title))
                    && (isbn.equals("*") || Long.toString(le.getBook().getISBN()).equals(isbn))
                    && (publisher.equals("*") || le.getBook().getPublisher().equals(publisher))) {
                if(authors.equals("*"))
                    books.add(le);
                else {
                    String leAuthors = le.getBook().getAuthors();
                    if(leAuthors.startsWith("{")) leAuthors = leAuthors.substring(1);
                    if(leAuthors.endsWith("}")) leAuthors = leAuthors.substring(0, leAuthors.length() - 1);

                    List<String> leAuthorsList = Arrays.asList(leAuthors.split(","));
                    boolean addBook = true;
                    for(String s : authors.split(",")) {
                        if(!leAuthorsList.contains(s)) {
                            addBook = false;
                            break;
                        }
                    }

                    if(addBook) books.add(le);
                }
            }
        }

        switch (sortOrder) {
            case "title":
                books.sort(Comparator.comparing(o -> o.getBook().getTitle()));
                break;
            case "publish-date":
                books.sort(Comparator.comparing(o -> o.getBook().getPublishDate()));
                break;
            case "book-status":
                books.sort(Comparator.comparingInt(LibraryEntry::getCopiesCheckedOut));
                break;
        }

        return new InfoResponse(books);
    }
}
