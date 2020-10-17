package main.Models;

import java.util.ArrayList;

/**
 * A model that represents each author and contains a list of all book ISBN's written by them
 * @author Alanna Morris
 */

public class Author {

    private String FirstName;
    private String LastName;
    private ArrayList<Integer> WrittenBookISBN = new ArrayList<>();

    /**
     * Creates an Author object using the name and a list of the ISBN's for any written books
     * @param FirstName String of first name
     * @param LastName String of last name
     * @param WrittenBookISBN ArrayList of ISBN's or an empty ArrayList
     */
    public Author(String FirstName, String LastName, ArrayList<Integer> WrittenBookISBN) {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.WrittenBookISBN = WrittenBookISBN;
    }

    /**
     * Adds a book ISBN to the ArrayList of the authors written books
     * @param ISBN book ISBN number
     */
    public void addISBN(Integer ISBN) {
        WrittenBookISBN.add(ISBN);
    }

}
