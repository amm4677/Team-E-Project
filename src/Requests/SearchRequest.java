package Requests;

import Resposes.*;
import main.Models.Book;
import main.Models.LibraryEntry;
import main.Models.OwningLibrary;

import java.util.*;
//todo: Dylan, please comment your code
public class SearchRequest implements Request {

    RequestNames.RequestName Command = RequestNames.RequestName.SEARCH;

    //a proxy for the library that the Visitor is being added to
    Collection<Book> books;

    private String title = "*";
    private String authors = "*";
    private String isbn = "*";
    private String publisher = "*";
    private String sortOrder = "title";


    public SearchRequest(Collection<Book> books, ArrayList<String> parameters) {
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
        this.books = books;
    }

    @Override
    public Response performRequest() {
        ArrayList<Book> books = new ArrayList<Book>();

        if(!(sortOrder.equals("title") || sortOrder.equals("publish-date") || sortOrder.equals("book-status")))
            return new InvalidSearchResponse();


        for (Book b : this.books) {
            if((title.equals("*") || b.getTitle().contains(title))
                    && (isbn.equals("*") || Long.toString(b.getISBN()).equals(isbn))
                    && (publisher.equals("*") || b.getPublisher().equals(publisher))) {
                if(authors.equals("*"))
                    books.add(b);
                else {
                    String leAuthors = b.getAuthors();
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

                    if(addBook) books.add(b);
                }
            }
        }

        switch (sortOrder) {
            case "title":
                books.sort(Comparator.comparing(Book::getTitle));
                break;
            case "publish-date":
                books.sort(Comparator.comparing(Book::getPublishDate));
                break;
        }

        return new SearchResponse(books);
    }
}
