package com.splitwise.models;

import com.splitwise.strategies.SplitStrategy;
import java.util.List;

public abstract class Expense {
    protected double amount;
    protected User paidBy;
    protected List<Split> splits;
    protected ExpenseType expenseType;
    protected SplitStrategy strategy;

    public Expense(double amount, User paidBy, List<Split> splits, ExpenseType type, SplitStrategy strategy) {
        this.amount = amount;
        this.paidBy = paidBy;
        this.splits = splits;
        this.expenseType = type;
        this.strategy = strategy;
    }

    public boolean validate() {
        return strategy.validate(splits, amount);
    }

    public List<Split> getSplits() {
        return splits;
    }

    public User getPaidBy() {
        return paidBy;
    }

    public double getAmount() {
        return amount;
    }
}
