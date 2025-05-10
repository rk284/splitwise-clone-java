package com.splitwise.models;

import java.util.List;

public class ExactExpense extends Expense {
    public ExactExpense(double amount, User paidBy, List<Split> splits) {
        super(amount, paidBy, splits, ExpenseType.EXACT);
    }

    @Override
    public boolean validate() {
        double total = 0;
        for (Split split : splits) {
            total += split.getAmount();
        }
        return total == this.amount;
    }
}
