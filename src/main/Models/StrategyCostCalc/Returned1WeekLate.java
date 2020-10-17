package main.Models.StrategyCostCalc;

import main.Models.CheckedOut;

/**
 * Calculates a transaction cost of 10$ when the book is returned 1-7 days past the due date
 * Strategy Pattern Concrete Operation
 *
 * @author Alanna Morris
 */

public class Returned1WeekLate implements TransactionCalculator{
    @Override
    public int calculateCost(CheckedOut checkedout) {
        return 10;
    }
}
