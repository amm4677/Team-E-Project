package main.Models.StrategyCostCalc;

import main.Models.CheckedOut;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Calculates a transaction cost by adding 2$ to the initial fine each week it is late
 * Weeks 3 to 11
 * Strategy Pattern Concrete Operation
 *
 * @author Alanna Morris
 */

public class Returned2To11WeeksLate implements TransactionCalculator{
    @Override
    public int calculateCost(CheckedOut checkedout) {
        Date today = new Date(0);
        int cost = 10;
        long difference;
        long differenceInDays;
        int weeksLate;

        difference = Math.abs(today.getTime() - checkedout.getDueDate().getTime());
        differenceInDays = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);

        weeksLate = (int) Math.ceil(differenceInDays/7);
        cost += 2 * (weeksLate - 2);

        return cost;
    }
}
