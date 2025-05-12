package com.splitwise.models;

import com.splitwise.strategies.SplitStrategy;

import java.util.List;

public class PercentExpense extends Expense {
    public PercentExpense(double amount, User paidBy, List<Split> splits, SplitStrategy strategy) {
        super(amount, paidBy, splits, ExpenseType.PERCENT, strategy);
    }
}
