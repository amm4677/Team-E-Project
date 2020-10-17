package main.Models.StateCheckOut;

/**
 * Implements CanCheckOut Interface by returning true if a uer can checkout more books
 * State Pattern Concrete Class
 *
 * @author Alanna Morris
 */

public class ableToCheckOut implements CanCheckOut{
    @Override
    public Boolean canCheckOut(checkOutState checkoutstate) {
        checkoutstate.setState(this);
        return true;
    }
}
