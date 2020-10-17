package main.Models;

import java.io.Serializable;
import java.util.Date;

/**
 * A model to represent a book in the Library Management System
 *
 * @author Joseph Saltalamacchia
 */
public class Book implements Serializable {

    private long ISBN;
    private String title;
    private String authors;
    private String publisher;
    private String publishDate;
    private int totalPages;
    private int totalCopies;
    private int copiesCheckedOut;

    /**
     * Creates a new Book object with a valid ISBN, publisher, date of publish, total number of pages, and total number of copies
     *
     * @param ISBN The unique identifying number for this book
     * @param publisher The publisher of this book
     * @param publishDate The date this book was published
     * @param totalPages  The total number of pages in a given copy of this book
     */
    //todo we should probably check that all of these values are valid
    public Book(long ISBN, String title, String authors, String publisher, String publishDate, int totalPages, int totalCopies) {

        this.ISBN = ISBN;
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.totalPages = totalPages;
        this.totalCopies = totalCopies;
        this.copiesCheckedOut = 0;
    }

    /**
     * returns the unique identification number for this book
     * @return the unique identification number for this book
     */
    public long getISBN() {
        return ISBN;
    }

    //There is on "set" method for the ISBN because it should never change

    /**
     * retruns this book's publisher
     * @return the name of this book's publisher
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * sets this books publisher to the entered string
     * @param publisher the new name of the publisher for this book
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * Returns the date this book was published
     * @return the date this book was published
     */
    public String getPublishDate() {
        return publishDate;
    }

    //there is no "set" method for the publish date because it should never change

    /**
     * The total number of pages in this book
     * @return the total number of pages in this book
     */
    public int getTotalPages() {
        return totalPages;
    }

    //there is no "set" method for the publish date because it should never change


    /**
     * returns the total number of copies of this book in the database
     * @return the total number of copies of this book in the database
     */
    public int getTotalCopies() {
        return totalCopies;
    }

    /**
     * Adds a specified number of copies of this book.
     * @param numberOfNewCopies the number of books to be added. Any numbers below zero are treated as zero
     * @return the new total number of copies
     */
    public int addCopies(int numberOfNewCopies) {
        if(numberOfNewCopies >= 0){
            this.totalCopies += numberOfNewCopies;
        }
        return this.totalCopies;
    }

    /**
     * removes a specified number of copies of this book.
     * @param numberOfCopies the number of books to be removed. Any numbers below zero are treated as zero
     * @return the new total number of copies
     */
    public int removeCopies(int numberOfCopies) {
        if(numberOfCopies >= 0 && numberOfCopies <= totalCopies){
            this.totalCopies -= numberOfCopies;
        }
        return this.totalCopies;
    }

    /**
     * returns the number of copies of this book currently checked out
     * @return the number of copies of this book currently checked out
     */
    public int getCopiesCheckedOut() {
        return copiesCheckedOut;
    }

    @Override
    public String toString() {
        return "Book{" +
                "ISBN=" + ISBN +
                ", publisher='" + publisher + '\'' +
                ", publishDate=" + publishDate +
                ", totalPages=" + totalPages +
                ", totalCopies=" + totalCopies +
                ", copiesCheckedOut=" + copiesCheckedOut +
                '}';
    }

    //todo I left this commented out for now because I'm not sure if we want to set error states for invalid numbers of
    // check-ins and check-outs

  /*  *//**
     * Check out additional copies of this book
     * @param numberOfCopies the number of copies of this book to be checked out. Numbers lower than zero are treated as
     *                       zero, numbers that, when added to the current number of books checked out, are greater than
     *                       the current total number of books are treated as being equal to the current total number of books
     * @return The current number of books checked out.
     *//*
    public int checkOut(int numberOfCopies){
        //if the number of copies attempting to be checked out if greater than the number of copies available, only
        // check out up to the number of copies available
        if((numberOfCopies + this.copiesCheckedOut) >= this.totalCopies){
            this.copiesCheckedOut = this.totalCopies;
        }
        if(numberOfCopies > 0 ){
            this.copiesCheckedOut += numberOfCopies;
        }
        return this.copiesCheckedOut;

    }

    *//**
     * Check in additional copies of this book
     * @param numberOfCopies the number of copies of this book to be checked in. Numbers lower than zero are treated as
     *                       zero, numbers greater than the total number of books currently checked out are treated as
     *                       being equal to the current total number of books checked out
     * @return The current number of books checked out.
     *//*
    public int checkIn(int numberOfCopies){
        //if the number of copies attempting to be checked in if greater than the number of copies currently checked out,
        // only check in up to the number of copies currently checked out
        if(numberOfCopies >= this.copiesCheckedOut){
            this.copiesCheckedOut = 0;
        }
        if(numberOfCopies > 0 ){
            this.copiesCheckedOut -= numberOfCopies;
        }
        return this.copiesCheckedOut;

    }*/
}
