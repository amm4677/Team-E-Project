package Requests;

public class Validator {

    protected static long validateAndParseLong(String longString){
        try{
            return(Long.parseLong(longString));
        }catch (NumberFormatException n){

            return (Long.valueOf(-1));
        }
    }

    protected static int validateAndParseInt(String intString){
        try{
            return(Integer.parseInt(intString));
        }catch (NumberFormatException n){

            return (-1);
        }
    }
}

