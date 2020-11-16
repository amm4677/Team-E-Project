package main.Models.Libraries;

import main.Models.Book;
import main.Models.Libraries.LibraryBase;
import main.Models.LibraryEntry;
import main.Models.Visit;
import main.Models.Visitor;

public class OpenLibrary extends LibraryBase {

    public OpenLibrary() {
        super();

        readVisitors();
        readBooks();

        libraryStatus = LibraryStatus.Open;
    }

    @Override
    public void addBook(Book book, int copies) {
        LibraryEntry entry = new LibraryEntry(book, copies);
        Inventory.put(book.getISBN(), entry);
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
    public Visit startVisit(int visitorID) {
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
    public void endVisit(int visitorID) {
        for (Visit v : Visits) {
            if (v.getVisitorID() == visitorID && v.getIsOngoingVisit()) {
                v.endVisit(time.getDate());
                return;
            }
        }
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
}
