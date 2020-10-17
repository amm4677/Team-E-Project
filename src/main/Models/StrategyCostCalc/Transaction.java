package main.Models.StrategyCostCalc;


import main.Models.CheckedOut;

/**
 * Uses the TransactionCalculator to complete the cost calculation
 * Strategy Pattern Context Class
 *
 * @author Alanna Morris
 */

public class Transaction {
    private TransactionCalculator transactionCalculator;

    public Transaction(TransactionCalculator transactionCalculator) {
        this.transactionCalculator = transactionCalculator;
    }

    public int calculate(CheckedOut checkedOut) {
        return transactionCalculator.calculateCost(checkedOut);
    }

}
