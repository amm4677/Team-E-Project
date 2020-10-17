package main.Models.StrategyCostCalc;

import main.Models.CheckedOut;

/**
 * Calculates a transaction cost of 0$ when the book is returned on or before the due date
 * Strategy Pattern Concrete Operation
 *
 * @author Alanna Morris
 */

public class ReturnedWithin7Days implements TransactionCalculator {
    @Override
    public int calculateCost(CheckedOut checkedOut) {
        return 0;
    }
}
