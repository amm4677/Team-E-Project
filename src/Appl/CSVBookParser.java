package Appl;


import main.Models.Book;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * This class parces a given text file of books using the following format:
 *
 * isbn,title,authors,publisher,published-date,page-count, book-status
 *
 * Books are converted into a datastructure and stored for future reference and use
 *
 * This is *only* for use in reading in the Book text file
 *
 * @author Joseph Saltalamacchia
 */
public class CSVBookParser {
    /**
     * A private helper method to parse a given book file into a Hash map of books, keyed by ISBN
     * @param file The line from the CSV file
     * @return A Book representation of the line being parsed
     */
    protected static HashMap<Long, Book> CreateBooks(File file) throws FileNotFoundException {

        //helpers to tell if we are parsing a title or author list so special characters are not ignored
        HashMap<Long, Book> allBooks = new HashMap<Long, Book>();
        Boolean inTitle = false;
        Boolean inAuthors = false;

        Scanner reader = new Scanner(new File(LibraryServer.BOOKSFILE));

        //iterate over every line
        while (reader.hasNext()) {

            String parameterString = "";

            String line = reader.nextLine();
            long ISBN = -1;
            String title = "";
            String authors = "";
            String publisher = "";
            String date = "";
            int totalPages = -1;

            int parameter = 0;

            for (char c : line.toCharArray()) {

                //if we are in the title or authors sections, append every character until notified to stop
                if (inTitle) {
                    if (c == '\"') {
                        inTitle = !inTitle;
                    } else {
                        parameterString = parameterString + c;
                    }
                } else if (inAuthors) {
                    if (c == '}' || c == '{') {
                        inAuthors = !inAuthors;
                    } else {
                        parameterString = parameterString + c;
                    }
                } else {

                    if (c == '\"') {
                        inTitle = !inTitle;
                        //check for lists of authors so they will not be interupted
                    } else if (c == '{' || c == '}') {

                        inAuthors = !inAuthors;
                    } else if (c == ',') {
                        //end the current parameter, assign it to the correct value, and prepare to start again
                        if (parameter == 0) {
                            ISBN = Long.parseLong( parameterString.trim());
                            //reset the string builder after getting the value
                            parameterString = "";
                            parameter++;
                        } else if (parameter == 1) {
                            title =  parameterString ;
                            //reset the string builder after getting the value
                            parameterString = "";
                            parameter++;
                        } else if (parameter == 2) {
                            authors = parameterString;
                            //reset the string builder after getting the value
                            parameterString = "";
                            parameter++;
                        } else if (parameter == 3) {
                            publisher = parameterString;
                            //reset the string builder after getting the value
                            parameterString = "";
                            parameter++;
                        } else if (parameter == 4) {
                            date = parameterString;
                            //reset the string builder after getting the value
                            parameterString = "";
                            parameter++;
                        }
                    }
                    if(c != '\"' && c != '{' && c != '}' && c != ',') {
                        parameterString = parameterString + c;
                    }
                }//end else

            }//end for loop


            totalPages = Integer.parseInt(parameterString.trim());
            Book b = new Book(ISBN, title, authors, publisher, date, totalPages, 1);
            allBooks.put(b.getISBN(), b);

           }//end while loop

        return (allBooks);

    }



    /**
     * Main method for testing.
     */
    public static void main(String[] args)
    {
        File file = new File("textFiles/books.txt");
        try
        {
            HashMap bunchaBooks = CreateBooks(file);

        }
        catch(FileNotFoundException error)
        {
            System.out.println("File not found.");
        }
    }

}
