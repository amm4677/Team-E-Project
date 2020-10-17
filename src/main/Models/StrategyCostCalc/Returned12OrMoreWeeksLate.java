package main.Models.StrategyCostCalc;

import main.Models.CheckedOut;

/**
 * Calculates a transaction cost of 30$ when the book is 12 or more weeks late
 * Strategy Pattern Concrete Operation
 *
 * @author Alanna Morris
 */

public class Returned12OrMoreWeeksLate implements TransactionCalculator {

    @Override
    public int calculateCost(CheckedOut checkedout) {
        return 30;
    }
}
