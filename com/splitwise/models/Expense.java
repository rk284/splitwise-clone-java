package com.splitwise.models;

import java.util.List;

public abstract class Expense {
    protected double amount;
    protected User paidBy;
    protected List<Split> splits;
    protected ExpenseType expenseType;

    public Expense(double amount, User paidBy, List<Split> splits, ExpenseType type) {
        this.amount = amount;
        this.paidBy = paidBy;
        this.splits = splits;
        this.expenseType = type;
    }

    public abstract boolean validate();
}
