package Requests;

/**
 * a helper class for parsing Strings into their numeric equivalents
 */
public class Validator {

    /**
     * Attempts to translate a String into a long, validation the string's ability to be translated in such a way
     * @param longString: the String that is attempting to be translated into a long
     * @return the long translation of the String, or -1 if it was untranslatable
     */
    protected static long validateAndParseLong(String longString){
        //attempt to parse the string
        try{
            return(Long.parseLong(longString));
        }catch (NumberFormatException n){
            //if it was not translatable, return a -1
            return (Long.valueOf(-1));
        }
    }

    /**
     * Attempts to translate a String into ai integer, validation the string's ability to be translated in such a way
     * @param intString: the String that is attempting to be translated into an integer
     * @return the integer translation of the String, or -1 if it was untranslatable
     */
    protected static int validateAndParseInt(String intString){
        //attempt to parse the string
        try{
            return(Integer.parseInt(intString));
        }catch (NumberFormatException n){
            //if it was not translatable, return a -1
            return (-1);
        }
    }
}

