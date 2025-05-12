package com.splitwise.models;

import com.splitwise.strategies.SplitStrategy;

import java.util.List;

public class ExactExpense extends Expense {
    public ExactExpense(double amount, User paidBy, List<Split> splits, SplitStrategy strategy) {
        super(amount, paidBy, splits, ExpenseType.EXACT, strategy);
    }
}
