package com.splitwise.models;

import java.util.List;

public class EqualExpense extends Expense {
    public EqualExpense(double amount, User paidBy, List<Split> splits) {
        super(amount, paidBy, splits, ExpenseType.EQUAL);
    }

    @Override
    public boolean validate() {
        return true; // no additional checks needed
    }
}
