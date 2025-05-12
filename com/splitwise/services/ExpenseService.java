package com.splitwise.services;

import com.splitwise.models.*;
import com.splitwise.strategies.*;

import java.util.*;

public class ExpenseService {

    private final Map<String, User> users;
    private final List<Expense> expenses;
    private final BalanceSheet balanceSheet;

    public ExpenseService() {
        this.users = new HashMap<>();
        this.expenses = new ArrayList<>();
        this.balanceSheet = new BalanceSheet();
    }

    public void addUser(String id, String name, String email, String mobile) {
        users.put(id, new User(id, name, email, mobile));
    }

    public User getUser(String id) {
        return users.get(id);
    }

    public void addExpense(ExpenseType type, double amount, String paidById, List<Split> splits) {
        User paidBy = users.get(paidById);
        Expense expense;

        switch (type) {
            case EQUAL -> {
                int totalSplits = splits.size();
                double share = amount / totalSplits;
                for (Split split : splits) {
                    split.setAmount(share);
                }
                expense = new EqualExpense(amount, paidBy, splits, new EqualSplitStrategy());
            }
            case EXACT -> {
                expense = new ExactExpense(amount, paidBy, splits, new ExactSplitStrategy());
            }
            case PERCENT -> {
                for (Split split : splits) {
                    if (!(split instanceof PercentSplit)) continue;
                    split.setAmount(amount * ((PercentSplit) split).getPercent() / 100.0);
                }
                expense = new PercentExpense(amount, paidBy, splits, new PercentSplitStrategy());
            }
            default -> throw new IllegalArgumentException("Invalid Expense Type");
        }

        if (!expense.validate()) {
            throw new IllegalArgumentException("Invalid expense splits.");
        }

        expenses.add(expense);
        for (Split split : splits) {
            if (!split.getUser().getId().equals(paidById)) {
                balanceSheet.updateBalance(paidById, split.getUser().getId(), split.getAmount());
            }
        }
    }

    public void showBalances() {
        balanceSheet.showBalances();
    }

    public void showBalance(String userId) {
        balanceSheet.showBalance(userId);
    }

    public void simplifyDebts() {
        System.out.println("Simplified Debt Settlements:");
        DebtSimplifier.simplify(balanceSheet.getBalanceSheet());
    }

  
}
