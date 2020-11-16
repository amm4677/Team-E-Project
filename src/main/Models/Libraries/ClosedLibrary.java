package main.Models.Libraries;

import main.Models.Book;
import main.Models.Libraries.LibraryBase;
import main.Models.Visit;
import main.Models.Visitor;

import java.io.Serializable;

/**
 * Represents the closed state of the library
 * @author Anthony Ferrioli
 * @author Joseph Saltalamacchia
 */
public class ClosedLibrary extends LibraryBase implements Serializable {

    public ClosedLibrary() {
        super();

        libraryStatus = LibraryStatus.Closed;
    }

    /**
     * Creates a new library that contains all of the information of the old library
     * @param oldLibrary the library being copied
     */
    public ClosedLibrary(LibraryBase oldLibrary){
        super();
        libraryStatus = LibraryStatus.Closed;
        this.Inventory = oldLibrary.getInventory();
        this.Register = oldLibrary.getRegister();
        this.Visits = oldLibrary.getVisits();
    }

    @Override
    public boolean addVisitor(Visitor visitor) {
        //Cannot add   visitor  as a visitor because the library is closed
        return false;
    }

    @Override
    public Visit startVisit(Long visitorID) {
        System.out.println("Visits cannot be started while the library is closed");

        return null;
    }

    @Override
    public Visit endVisit(Long visitorID) {
        System.out.println("There are no visits to end because the library is currently closed");
        return null;
    }

    @Override
    public boolean visitorCheckOut(Visitor visitor, Long ISBN) {
        System.out.println("Visitors cannot check out books because the library is currently closed");

        return false;
    }

    @Override
    public boolean borrowBook(Long VisitorID, Long ISBN) {
        System.out.println("cannot check out a book while the library is closed");
        return false;
    }
}
