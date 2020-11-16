package main.Models.Libraries;

import main.Models.OwningLibrary;

import java.io.*;

/**
 * A helper class for reading in and saving a given library
 * @author Joseph Saltalamacchia
 */
public class LibrarySaveAndRead {

    /**
     * Saves the library to an external bin file
     * @param library the library being saved
     * @return true if the library was saved, false otherwise
     */
    public static boolean saveLibrary(LibraryBase library) {
        try {
            //create a writer for the Books
            FileOutputStream fLibrary = new FileOutputStream(new File("TextFiles/Library.bin"));
            ObjectOutputStream oLibrary = new ObjectOutputStream(fLibrary);

            oLibrary.writeObject(library);
            //todo: delete this print statement before the final submission
            System.out.println(library.toString());

            fLibrary.close();
            oLibrary.close();
            return true;

        } catch (FileNotFoundException f) {
            System.out.println("Book File Not Found");
        } catch (IOException i) {
            System.out.println("Error initializing stream");
        }

        return false;
    }

    /**
     * reads in the library from an external bin file, and converts it into an owning library obejct
     * @return the library that was read in
     */
    public static LibraryBase openLibrary() {

        LibraryBase oldLibrary = null;

        try {
            FileInputStream fLibrary = new FileInputStream(new File("TextFiles/Library.bin"));
            ObjectInputStream oLibrary = new ObjectInputStream(fLibrary);

            try {
               Object inputLibrary =  oLibrary.readObject();
               if(inputLibrary instanceof OpenLibrary){
                   oldLibrary = (OpenLibrary) inputLibrary;
               }
               else if(inputLibrary instanceof ClosedLibrary){
                   oldLibrary = (ClosedLibrary) inputLibrary;
                }

            } catch (EOFException ignored) {
            }

            fLibrary.close();
            oLibrary.close();

            //todo: delete this print statement before the final submission
            System.out.println(oldLibrary);
            return oldLibrary;

        } catch (FileNotFoundException f) {
            System.out.println("VisitorLog file not found");
        } catch (IOException i) {
            System.out.println("No Visitors registered in library");
        } catch (ClassNotFoundException c) {
            System.out.println("could not find class");
        }

        return oldLibrary;
    }
}
