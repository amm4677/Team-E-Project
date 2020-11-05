package Requests;

import Resposes.InfoResponse;
import Resposes.Response;
import main.Models.LibraryEntry;
import main.Models.OwningLibrary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InfoRequest implements Request {
    RequestNames.RequestName Command = RequestNames.RequestName.INFO;


    //a proxy for the library that the Visitor is being added to
    private OwningLibrary proxyLibrary;

    private String title = "*";
    private String authors = "*";
    private String isbn = "*";
    private String publisher = "*";
    private String sortOrder = "title";


    public InfoRequest(OwningLibrary library, ArrayList<String> parameters) {
        switch(parameters.size()) {
            default:
                break;
            case 5:
                this.sortOrder = parameters.get(4);
            case 4:
                this.publisher = parameters.get(3);
            case 3:
                this.isbn = parameters.get(2);
            case 2:
                this.authors = parameters.get(1);
                if(authors.startsWith("{")) authors = authors.substring(1);
                if(authors.endsWith("}")) authors = authors.substring(0, authors.length() - 1);
                this.title = parameters.get(0);
                break;
        }
        proxyLibrary = library;
    }

    @Override
    public Response performRequest() {
        ArrayList<LibraryEntry> books = new ArrayList<LibraryEntry>();


        for (LibraryEntry le : proxyLibrary.getInventory().values()) {
            if((title.equals("*") || le.getBook().getTitle().contains(title))
            && (isbn.equals("*") || Long.toString(le.getBook().getISBN()).equals(isbn))
            && (publisher.equals("*") || le.getBook().getTitle().equals(publisher))) {
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

        return new InfoResponse(books);
    }
}
