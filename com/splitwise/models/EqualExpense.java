package com.splitwise.models;

import com.splitwise.strategies.SplitStrategy;

import java.util.List;

public class EqualExpense extends Expense {
    public EqualExpense(double amount, User paidBy, List<Split> splits, SplitStrategy strategy) {
        super(amount, paidBy, splits, ExpenseType.EQUAL, strategy);
    }
}
