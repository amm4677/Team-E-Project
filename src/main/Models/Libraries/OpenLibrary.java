package main.Models.Libraries;

import main.Models.Book;
import main.Models.Libraries.LibraryBase;
import main.Models.LibraryEntry;
import main.Models.Visit;
import main.Models.Visitor;

import java.io.Serializable;

/**
 * Acts as the open state for the library. This is one of the concrete states in the state pattern for the library
 *
 * @author Anthony Ferrioli
 * @author Joseph Saltalamacchia
 */
public class OpenLibrary extends LibraryBase implements Serializable {

    public OpenLibrary() {
        super();

        libraryStatus = LibraryStatus.Open;
    }

    /**
     * Creates a new library that contains all of the information of the old library
     * @param oldLibrary the library being copied
     */
    public OpenLibrary(LibraryBase oldLibrary){
        super();
        libraryStatus = LibraryStatus.Open;
        this.Inventory = oldLibrary.getInventory();
        this.Register = oldLibrary.getRegister();
        this.Visits = oldLibrary.getVisits();
    }

    @Override
    public void addVisitor(Visitor visitor) {
        Long ID = visitor.getID();
        Register.put(ID, visitor);
    }

    /**
     * Starts the visit of a registered visitor
     *
     * @param visitorID the visitor checking out the book, assuming they are not checked in
     * @return the Visit object representing the status of their visit
     */
    @Override
    public Visit startVisit(Long visitorID) {
        //Checks to make sure they aren't already checked in (visitors can check in multiple times per day)
        for (Visit v : Visits) {
            if (v.getVisitorID() == visitorID && v.getIsOngoingVisit()) return v;
        }

        Visit v = new Visit(visitorID, time.getDate());
        Visits.add(v);

        return v;
    }

    /**
     * Ends the present visit of the visitor, if they are currently checked in
     *
     * @param visitorID the visitor visiting the library
     */
    @Override
    public Visit endVisit(Long visitorID) {
        for (Visit v : Visits) {
            if (v.getVisitorID() == visitorID && v.getIsOngoingVisit()) {
                v.endVisit(time.getDate());
                return v;
            }
        }

        return null;
    }

    /**
     * Allows a given visitor to check out a book
     *
     * @param visitor the visitor checking out the book
     * @param ISBN    the ISBN of the book being checked out
     * @return if the visitor successfully checked out the book or not
     */
    @Override
    public boolean visitorCheckOut(Visitor visitor, Long ISBN) {
        if (Inventory.containsKey(ISBN)) {
            //retrive the book objject that the Library entry is wrapping
            Book book = Inventory.get(ISBN).getBook();
            if (Inventory.get(ISBN).canBeCheckedOut() && visitor.addCheckedOutBook(book)) {
                Inventory.get(ISBN).checkoutBook();
                return true;
            }
        }
        return false;
    }

    @Override
    /**
     * Allows a visitor to borrow a book from the library
     * @param VisitorID the ID of the visitor attempting to check out the book
     * @param ISBN the ISBN of the book attempting to be checked out
     * @return true if the book was sussessfully checked out, false otherwise
     */
    public boolean borrowBook(Long VisitorID, Long ISBN) {
        Inventory.get(ISBN).checkoutBook();
        return Register.get(VisitorID).addCheckedOutBook(Inventory.get(ISBN).getBook());
    }

}
