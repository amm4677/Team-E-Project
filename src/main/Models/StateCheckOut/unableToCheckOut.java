package main.Models.StateCheckOut;

/**
 * Implements CanCheckOut Interface by returning false if a uer can not checkout more books
 * State Pattern Concrete Class
 *
 * @author Alanna Morris
 */

public class unableToCheckOut implements CanCheckOut {
    @Override
    public Boolean canCheckOut(checkOutState checkoutstate) {
        checkoutstate.setState(this);
        return false;
    }
}
