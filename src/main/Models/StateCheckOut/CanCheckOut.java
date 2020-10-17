package main.Models.StateCheckOut;

/**
 * Interface used to determine if more books can be checked out by a visitor
 * State Pattern State Interface
 *
 * @author Alanna Morris
 */

public interface CanCheckOut {
    public Boolean canCheckOut(checkOutState checkoutstate);
}
