package main.Models.StateCheckOut;

/**
 * Uses the CanCheckOut Interface to determine if more books can be checked out
 * State Pattern Context class
 *
 * @author Alanna Morris
 */

public class checkOutState {

    private CanCheckOut canCheckOut;

    public checkOutState() {
        canCheckOut = null;
    }

    public void setState(CanCheckOut canCheckOut) {
        this.canCheckOut = canCheckOut;
    }

    public CanCheckOut getState() {
        return canCheckOut;
    }

}
