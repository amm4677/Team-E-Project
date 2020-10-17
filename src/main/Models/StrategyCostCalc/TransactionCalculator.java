package main.Models.StrategyCostCalc;

import main.Models.CheckedOut;

/**
 * Interface used to calculate the transaction
 * Strategy Pattern Strategy Interface
 */

public interface TransactionCalculator {
    public int calculateCost(CheckedOut checkedout);
}
