package com.splitwise.models;

import java.util.List;

public class PercentExpense extends Expense {
    public PercentExpense(double amount, User paidBy, List<Split> splits) {
        super(amount, paidBy, splits, ExpenseType.PERCENT);
    }

    @Override
    public boolean validate() {
        double totalPercent = 0;
        for (Split split : splits) {
            if (!(split instanceof PercentSplit)) return false;
            totalPercent += ((PercentSplit) split).getPercent();
        }
        return totalPercent == 100;
    }
}
