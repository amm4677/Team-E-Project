package main.Models;

import Appl.LibraryServer;

public class ClosedLibrary extends LibraryBase {

    public ClosedLibrary() {
        super();

        libraryStatus = LibraryStatus.Closed;
    }

    @Override
    public void closeLibrary() {
        System.out.println("Library closed");
    }

    @Override
    public void addBook(Book book, int copies) {
        System.out.println("Cannot add " + copies + " of " + book + " because the library is currently closed");
    }

    @Override
    public void addVisitor(Visitor visitor) {
        System.out.println("Cannot add " + visitor + " as a visitor because the library is closed");
    }

    @Override
    public Visit startVisit(int visitorID) {
        System.out.println("Visits cannot be started while the library is closed");

        return null;
    }

    @Override
    public void endVisit(int visitorID) {
        System.out.println("There are no visits to end because the library is currently closed");
    }

    @Override
    public boolean visitorCheckOut(Visitor visitor, Long ISBN) {
        System.out.println("Visitors cannot check out books because the library is currently closed");

        return false;
    }
}
