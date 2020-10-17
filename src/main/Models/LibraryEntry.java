package main.Models;

import java.io.Serializable;

/**
 * Book class with # of available copies and # of checked out copies
 */

public class LibraryEntry implements Serializable {

    private Book book;
    private int totalCopies;
    private int copiesCheckedOut;

    public LibraryEntry(Book Book, int totalCopies) {
        this.book = book;
        this.totalCopies = totalCopies;
        this.copiesCheckedOut = 0;
    }

    public void buyMoreCopies(int amountBought) {
        totalCopies += amountBought;
    }

    public void checkoutBook() {
        this.copiesCheckedOut += 1;
    }

    public void checkinBook() {
        this.copiesCheckedOut -= 1;
    }

    public Boolean canBeCheckedOut() {
        return (this.totalCopies > this.copiesCheckedOut);
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public int getCopiesCheckedOut() {
        return copiesCheckedOut;
    }

    public Book getBook(){
        return book;
    }

    public long getISBN(){
        return book.getISBN();
    }

    @Override
    public String toString() {
        return "LibraryEntry{" +
                "ISBN=" + book.getISBN() +
                ", totalCopies=" + totalCopies +
                ", copiesCheckedOut=" + copiesCheckedOut +
                '}';
    }
}
